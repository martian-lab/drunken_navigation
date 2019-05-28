package com.martianlab.drunkennavigation.domain

import androidx.lifecycle.LiveData
import com.martianlab.drunkennavigation.data.db.entities.Point
import com.martianlab.drunkennavigation.presentation.viewmodel.QRItem

interface DrunkRepository {

    fun addPoint(item:QRItem)

    fun getPoints() : LiveData<List<Point>>
}