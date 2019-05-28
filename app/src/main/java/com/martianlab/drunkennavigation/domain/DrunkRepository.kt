package com.martianlab.drunkennavigation.domain

import com.martianlab.drunkennavigation.presentation.viewmodel.QRItem

interface DrunkRepository {

    fun addPoint(item:QRItem)
}