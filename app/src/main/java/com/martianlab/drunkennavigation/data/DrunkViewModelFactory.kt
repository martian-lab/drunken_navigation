package com.martianlab.drunkennavigation.data


import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.martianlab.drunkennavigation.domain.DrunkRepository
import com.martianlab.drunkennavigation.presentation.viewmodel.ScanViewModel
import javax.inject.Singleton

@Singleton
class DrunkViewModelFactory @Inject internal constructor(repository: DrunkRepositoryImpl) : ViewModelProvider.Factory {


    internal var repository: DrunkRepository

    init {
        this.repository = repository
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ScanViewModel::class.java) {
            return ScanViewModel(repository) as T
        }
        throw IllegalArgumentException()
    }
}
