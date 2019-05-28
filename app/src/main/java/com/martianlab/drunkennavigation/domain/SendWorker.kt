package com.martianlab.drunkennavigation.domain

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.model.DNaviService
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SendWorker @Inject constructor(
    private val context: Context,
    private val params : WorkerParameters,
    private val appExecutors: AppExecutors,
    private val pointsDao: PointsDao,
    private val dNaviService: DNaviService

): Worker(context, params) {


    override fun doWork(): Result {
        send()
        return Result.success()
    }

    fun send(){
        val points = pointsDao.getAllUnsentBySessId("000")
        for( point in points.value.orEmpty() ) {
            dNaviService.postValues( point.id.toString(), point.guid, point.time, point.text )
        }
    }

}