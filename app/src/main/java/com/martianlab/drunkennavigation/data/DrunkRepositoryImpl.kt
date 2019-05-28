package com.martianlab.drunkennavigation.model

import androidx.lifecycle.LiveData
import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.data.db.TochResponse
import com.martianlab.drunkennavigation.data.db.entities.Point
import com.martianlab.drunkennavigation.domain.DNaviService
import com.martianlab.drunkennavigation.domain.DrunkRepository
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import com.martianlab.drunkennavigation.presentation.viewmodel.QRItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import javax.inject.Inject
import kotlin.random.Random


class DrunkRepositoryImpl @Inject constructor(
    private val appExecutors: AppExecutors,
    private val pointsDao: PointsDao,
    private val dNaviService: DNaviService
) : DrunkRepository {

    val runGuid : String = "000"

    override fun getPoints(): LiveData<List<Point>> {

        return pointsDao.getAllBySessId(runGuid)
    }

    override fun addPoint(item: QRItem) {

        appExecutors.diskIO().execute {
            val id = Random.nextLong()
            val point = Point(id, runGuid, Date().time, item.text, item.type, false)

            println(point)

            pointsDao.insert( point )

            //println("point=" + pointsDao.getById(id).value )
            println( "points size=" + pointsDao.getAllUnsent().size )

            dNaviService.postValues( id.toString(), runGuid, item.time, item.text ).enqueue( object :
                Callback<TochResponse> {
                override fun onFailure(call: Call<TochResponse>, t: Throwable) {

                    println("on failure, t=" + t.message )
                    //do smth
                }

                override fun onResponse(call: Call<TochResponse>, response: Response<TochResponse>) {
                    println("on success")
                    if (response.isSuccessful()) {
                        pointsDao.setSent(id)
                    } else {
                        // Handle other responses
                    }
                }

            })
        }
    }
}