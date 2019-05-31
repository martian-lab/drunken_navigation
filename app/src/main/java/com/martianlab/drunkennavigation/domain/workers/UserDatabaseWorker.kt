/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.martianlab.drunkennavigation.domain.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.martianlab.drunkennavigation.data.db.USER_DATA_FILENAME
import com.martianlab.drunkennavigation.data.db.UserDao
import com.martianlab.drunkennavigation.data.db.entities.User
import com.martianlab.drunkennavigation.domain.DNaviService
import com.martianlab.drunkennavigation.domain.tools.ApiSuccessResponse
import com.martianlab.drunkennavigation.model.tools.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val userDao: UserDao,
    private val appExecutors: AppExecutors,
    private val service: DNaviService
) : Worker(context, workerParams) {

    private val TAG by lazy { UserDatabaseWorker::class.java.simpleName }

    override fun doWork(): Result =

        try {
            val userList = service.getUsers().enqueue(object :
                Callback<List<User>> {
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        println("error seeding database!" + t.message)
                    }

                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {

                        appExecutors.networkIO().execute {
                            userDao.insertAll(response.body().orEmpty())
                        }
                        println("success seeding database!")
                        Result.success()
                    }

            })
            Result.success()
//            applicationContext.assets.open(USER_DATA_FILENAME).use { inputStream ->
//                JsonReader(inputStream.reader()).use { jsonReader ->
//                    val userType = object : TypeToken<List<User>>() {}.type
//                    val userList: List<User> = Gson().fromJson(jsonReader, userType)
//                    println("userList=" + userList)
//
//                    appExecutors.networkIO().execute {
//                        userDao.insertAll(userList)
//                    }
//                    println("success seeding database!")
//                    Result.success()
//                }
//            }
        } catch (ex: Exception) {
            Log.e(TAG, "Error seeding database", ex)
            Result.failure()
        }

}