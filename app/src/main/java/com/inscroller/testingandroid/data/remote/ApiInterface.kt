package com.inscroller.testingandroid.data.remote

import com.inscroller.testingandroid.BuildConfig
import com.inscroller.testingandroid.data.remote.responses.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/api/")
    fun getImages(
        @Query("q") query: String,
        @Query("key") key: String = BuildConfig.API_KEY
    ): Response<ImageResponse>
}