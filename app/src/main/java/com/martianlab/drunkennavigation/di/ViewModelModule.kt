package com.martianlab.drunkennavigation.di

import androidx.lifecycle.ViewModelProvider
import com.martianlab.drunkennavigation.data.DrunkViewModelFactory
import com.martianlab.drunkennavigation.domain.DrunkRepository
import com.martianlab.drunkennavigation.model.DNaviRepository


import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface ViewModelModule {


    @Singleton
    @Binds
    fun provideLoginRepository(drunkRepository: DNaviRepository): DrunkRepository

    @Singleton
    @Binds
    fun bindViewModelFactory(viewModelFactory: DrunkViewModelFactory): ViewModelProvider.Factory
}
