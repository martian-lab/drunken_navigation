package com.martianlab.drunkennavigation.model

import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.data.db.entities.Point
import com.martianlab.drunkennavigation.domain.DrunkRepository
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import com.martianlab.drunkennavigation.presentation.viewmodel.QRItem
import javax.inject.Inject
import kotlin.random.Random


class DNaviRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val pointsDao: PointsDao,
    private val dNaviService: DNaviService
) : DrunkRepository {

    val runGuid : String = "000"


    override fun addPoint(item: QRItem) {

        pointsDao.insertAll( Point(Random.nextLong(), runGuid, item.time, item.text, item.type, false ))
    }
}