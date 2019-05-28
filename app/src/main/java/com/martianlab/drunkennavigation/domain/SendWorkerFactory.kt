package com.martianlab.drunkennavigation.domain

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.martianlab.drunkennavigation.DNaviApp
import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import java.lang.IllegalArgumentException
import javax.inject.Inject


class SendWorkerFactory() : WorkerFactory(){

    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var pointsDao: PointsDao
    @Inject
    lateinit var dNaviService: DNaviService

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        (appContext.applicationContext as DNaviApp).component.inject(this)
        println(workerClassName)
        return when( workerClassName ) {
            "com.martianlab.drunkennavigation.domain.SendWorker" -> SendWorker(appContext, workerParameters, appExecutors, pointsDao, dNaviService)
            else -> throw IllegalArgumentException("wrong worker class")
        }
    }


}