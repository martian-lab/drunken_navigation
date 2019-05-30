/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.martianlab.drunkennavigation.di

import android.app.Application
import com.martianlab.drunkennavigation.DrunkApp
import com.martianlab.drunkennavigation.domain.SendWorkerFactory
import com.martianlab.drunkennavigation.presentation.ListFragment
import com.martianlab.drunkennavigation.presentation.LoginFragment
import com.martianlab.drunkennavigation.presentation.ScanFragment
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, MainActivityModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

//    companion object {
//
//        // For Singleton instantiation
//        @Volatile
//        private var instance: AppComponent? = null
//
//        fun getInstance(context: Context): AppComponent {
//            return instance ?: synchronized(this) {
//                instance ?: buildDatabase(context).also { instance = it }
//            }
//        }
//    }

    fun inject(app: DrunkApp)
    fun inject(fragment : ListFragment )
    fun inject(fragment : ScanFragment )
    fun inject(fragment : LoginFragment)
    fun inject(factory : SendWorkerFactory)

}
