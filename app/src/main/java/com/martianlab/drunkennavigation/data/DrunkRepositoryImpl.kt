package com.martianlab.drunkennavigation.data

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
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

    private val userId : Int
    lateinit var runGuid : String
    var state = NaviState.WAIT

    init{
        userId = preferences.getInt("user_id", 0)
        runGuid = "000"
    }




    override fun getPoints(): LiveData<List<Point>> {

        return pointsDao.getAllBySessId(runGuid)
    }



    override fun addPoint(text: String ) {

        if( !isTochilovs(text) )
            return

        appExecutors.diskIO().execute {
            val id = Random.nextLong()
            val point = Point(id, runGuid, Date().time, text, getPointType(text).num )

            pointsDao.insert( point )

            dNaviService.postValues( TOKEN, userId, runGuid, point.time, text ).enqueue( object :
                Callback<TochResponse> {
                override fun onFailure(call: Call<TochResponse>, t: Throwable) {

                    println("on failure, t=" + t.message )
                    //do smth
                }

                override fun onResponse(call: Call<TochResponse>, response: Response<TochResponse>) {

                    println("on success, response=" + response.message() )

                    if (response.isSuccessful()) {
                        pointsDao.setSent(id)
                    } else {
                        // Handle other responses
                    }
                }

            })
        }

    }

    override fun getUserByPin(pin: Int) = userDao.getByPin(pin)


    override fun getUser(id: Int) = userDao.getById( id )



    fun getPointType(text: String):Points{
        val point = text.substring(24,text.length-1)
        return Points.getPointByText(point.substring(0,1))
    }

    fun isTochilovs(text: String):Boolean = text.contains("http://dr.tochilov.ru/c/")
}


