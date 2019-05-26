package com.martianlab.drunkennavigation.di

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject


/**
 * Activity with Dagger
 *
 * This class is used to retain all the dagger boilerplate in it,
 * so the child classes could show just business logic
 *
 */
abstract class DaggerActivity : FragmentActivity(){
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

    }

    fun fragmentInjector() = dispatchingAndroidInjector

}
