package com.careerproject.lifelog.ui.main.check

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsRetrofitClient {
    private const val URL = "https://api.odcloud.kr/api/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService() : NewsRetrofitService {
        return retrofit.create(NewsRetrofitService::class.java)
    }
}