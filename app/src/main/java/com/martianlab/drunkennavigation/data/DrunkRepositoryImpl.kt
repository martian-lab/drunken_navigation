package com.martianlab.drunkennavigation.data

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.data.db.TochResponse
import com.martianlab.drunkennavigation.data.db.UserDao
import com.martianlab.drunkennavigation.data.db.entities.Point
import com.martianlab.drunkennavigation.data.db.entities.User
import com.martianlab.drunkennavigation.domain.DNaviService
import com.martianlab.drunkennavigation.domain.DrunkRepository
import com.martianlab.drunkennavigation.domain.NaviState
import com.martianlab.drunkennavigation.domain.Points
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.IllegalArgumentException
import java.sql.Timestamp
import java.util.*
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

const val TOKEN = "8a4deb84-1686-4a08-b32b-f0cbec5d8940"

@Singleton
class DrunkRepositoryImpl @Inject constructor(
    private val appExecutors: AppExecutors,
    private val pointsDao: PointsDao,
    private val userDao: UserDao,
    private val dNaviService: DNaviService,
    private val preferences: SharedPreferences
) : DrunkRepository {

    private var userId : Int
    private var runGuid : String? = null
    var state : NaviState
    var stateLD : MutableLiveData<NaviState> = MutableLiveData()
    var user = MutableLiveData<User>()
    var list = MutableLiveData<List<Point>>()

    init{
        state = when( preferences.getInt("curr_state", -1) ){
            1 -> NaviState.RUN
            else -> NaviState.WAIT
        }
        stateLD.value = state
        userId = preferences.getInt("user_id", -1)
        user.observeForever({
            it?.let {
                userId = it.id
                preferences.edit().putInt("user_id", it.id).apply()
            }
        })
    }




    override fun getPoints(): LiveData<List<Point>> {
        println("get point")

        val res = if( runGuid != null ) {
            println( "get by guid=" + runGuid)
            pointsDao.getAllBySessId(runGuid!!)
        }
        else MutableLiveData<List<Point>>()

        res.observeForever{list.value = it}

        println("get point res=" + res )

        return list
    }




    override fun addPoint(text: String) {

        if( !isTochilovs(text) )
            return

        when( getPointType(text) ){
            Points.START ->
                if( state == NaviState.WAIT ){
                    runGuid = UUID.randomUUID().toString()
                    state = NaviState.RUN
                    stateLD.value = state
                }else
                    return

            Points.AE, Points.KP ->
                if( state != NaviState.RUN )
                    return

            Points.FINISH ->
                if( state != NaviState.RUN )
                    return
//                else{
//                    state = NaviState.WAIT
//                    stateLD.value = state
//                }

        }

        println("point type=" + getPointType(text) )


        appExecutors.diskIO().execute {
            val id = Random.nextLong()

            val point = Point(id, runGuid!!, Date().time, getPoint(text), getPointType(text).num )
            println("point =" + point)

            pointsDao.insert( point )

            dNaviService.postValues( TOKEN, userId, runGuid!!, point.time, getPoint(text) ).enqueue( object :
                Callback<Unit> {
                override fun onFailure(call: Call<Unit>, t: Throwable) {

                    println("on failure, t=" + t.message )
                    //do smth
                }

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {

                    if (response.isSuccessful()) {
                        appExecutors.diskIO().execute{pointsDao.setSent(id)}
                    } else {
                        // Handle other responses
                    }
                }

            })

            appExecutors.mainThread().execute{
                if( getPointType(text) == Points.FINISH ) {
                    runGuid = null
                    state = NaviState.WAIT
                    stateLD.value = state
                }
            }





        }

//        when( getPointType(text) ){
//
//            Points.FINISH ->
//                if( state == NaviState.RUN ){
//                    state = NaviState.WAIT
//                    stateLD.value = state
//                    runGuid = null
//                }
//        }

    }

    override fun getUserByPin(pin: Int):LiveData<User> {
        val res = userDao.getByPin(pin)
        res.observeForever { user.value = it }

        return user
    }


    override fun getUser(id: Int) = userDao.getById( id )

    override fun logout(){
        preferences.edit().putInt("user_id", -1).apply()
        user.value = null
        userId = -1
        runGuid = null
        state = NaviState.WAIT
        stateLD.value = state
        list.value = emptyList()
        println("logout")
    }

    fun getPointType(text: String):Points = Points.getPointByText(getPoint(text).substring(0,1))
    fun getPoint(text: String) = text.substring(24,text.length-1)

    override fun isTochilovs(text: String):Boolean = text.contains("http://dr.tochilov.ru/c/")

    override fun isFinish( text: String) : Boolean = getPointType(text) == Points.FINISH

    override fun isLogged() = userId > 0


    override fun getState() = stateLD
}


