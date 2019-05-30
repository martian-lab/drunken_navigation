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

package com.martianlab.drunkennavigation

import android.app.Activity
import android.app.Application
import android.util.Log
import androidx.work.*
import com.martianlab.drunkennavigation.di.AppComponent
import com.martianlab.drunkennavigation.di.DaggerAppComponent
import com.martianlab.drunkennavigation.domain.workers.SendWorker
import com.martianlab.drunkennavigation.domain.SendWorkerFactory
import com.martianlab.drunkennavigation.domain.workers.UserDatabaseWorker
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class DrunkApp : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var component : AppComponent

    override fun onCreate() {
        super.onCreate()
        component =  DaggerAppComponent.builder().application(this).build()
        component.inject(this)

        val configuration = Configuration.Builder()
            .setMinimumLoggingLevel(Log.VERBOSE)
            .setWorkerFactory(SendWorkerFactory())
            .build()

        WorkManager.initialize(this, configuration)

        val workRequest : PeriodicWorkRequest.Builder = PeriodicWorkRequest.Builder(SendWorker::class.java, 24, TimeUnit.HOURS)

        WorkManager.getInstance().enqueueUniquePeriodicWork("sendTochUpdates", ExistingPeriodicWorkPolicy.KEEP, workRequest.build() )

        val seedRequest : OneTimeWorkRequest.Builder = OneTimeWorkRequest.Builder(UserDatabaseWorker::class.java)

        WorkManager.getInstance().enqueue(seedRequest.build() )
    }



    override fun activityInjector() = dispatchingAndroidInjector
}
