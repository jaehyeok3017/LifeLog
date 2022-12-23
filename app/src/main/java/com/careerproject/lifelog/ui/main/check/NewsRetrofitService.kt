package com.careerproject.lifelog.ui.main.check

import com.careerproject.lifelog.data.news.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRetrofitService {
    @GET("15097922/v1/uddi:27150cfe-98f8-4608-9036-d68907d18fad")
    fun getNewsData(
        @Query("serviceKey") serviceKey: String
    ) : Call<NewsData>
}