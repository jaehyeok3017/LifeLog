package com.careerproject.lifelog.ui.main.check

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

object NewsRetrofitClient {
    private const val URL = "https://api.odcloud.kr/api/"
    val httpLoggingInterceptor = HttpLoggingInterceptor()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .client(provideOkHttpClient(AppInterceptor()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService() : NewsRetrofitService {
        return retrofit.create(NewsRetrofitService::class.java)
    }

    private fun provideOkHttpClient(
        interceptor: AppInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .run {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(interceptor)
            build()
        }

    class AppInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain)
                : Response = with(chain) {
            val newRequest = request().newBuilder()
                .addHeader("X-Naver-Client-Id", "33chRuAiqlSn5hn8tIme")
                .addHeader("X-Naver-Client-Secret", "fyfwt9PCUN")
                .build()

            proceed(newRequest)
        }
    }
}