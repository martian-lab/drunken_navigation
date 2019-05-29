package com.martianlab.drunkennavigation.model

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.data.db.TochResponse
import com.martianlab.drunkennavigation.data.db.UserDao
import com.martianlab.drunkennavigation.data.db.entities.Point
import com.martianlab.drunkennavigation.data.db.entities.User
import com.martianlab.drunkennavigation.domain.DNaviService
import com.martianlab.drunkennavigation.domain.DrunkRepository
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import com.martianlab.drunkennavigation.presentation.viewmodel.QRItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class DrunkRepositoryImpl @Inject constructor(
    private val appExecutors: AppExecutors,
    private val pointsDao: PointsDao,
    private val userDao: UserDao,
    private val dNaviService: DNaviService,
    private val preferences: SharedPreferences
) : DrunkRepository {

    private val userId : Long
    lateinit var runGuid : String

    init{
        userId = preferences.getLong("user_id", 0)
        runGuid = "000"
    }




    override fun getPoints(): LiveData<List<Point>> {

        return pointsDao.getAllBySessId(runGuid)
    }

    override fun addPoint(item: QRItem) {

        appExecutors.diskIO().execute {
            val id = Random.nextLong()
            val point = Point(id, runGuid, Date().time, item.text, item.type, false)

            pointsDao.insert( point )

            dNaviService.postValues( id.toString(), runGuid, item.time, item.text ).enqueue( object :
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


    override fun getUser(id: Long) = userDao.getById( id )
}