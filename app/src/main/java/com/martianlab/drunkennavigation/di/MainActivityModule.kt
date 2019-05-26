package com.martianlab.drunkennavigation.di


import com.martianlab.drunkennavigation.presentation.MainActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}
