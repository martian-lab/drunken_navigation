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

package com.martianlab.drunkennavigation.model


import com.martianlab.drunkennavigation.data.db.TochResponse
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * REST API access points
 */
interface DNaviService {

    @POST("/rest/")
    fun postValues(
        @Query("token") token: String,
        @Query("run_guid") run_guid: String,
        @Query("ts") ts: String,
        @Query("text") test: String
    ): Call<TochResponse>
}
