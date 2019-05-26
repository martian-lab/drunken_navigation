package com.martianlab.drunkennavigation.model

import com.martianlab.drunkennavigation.data.db.PointsDao
import com.martianlab.drunkennavigation.domain.DrunkRepository
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import javax.inject.Inject


class DNaviRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val pointsDao: PointsDao,
    private val dNaviService: DNaviService
) : DrunkRepository {


}