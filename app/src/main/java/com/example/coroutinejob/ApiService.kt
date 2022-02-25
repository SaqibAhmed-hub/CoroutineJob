package com.example.coroutinejob

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface ApiService {
    @GET("users/2")
    suspend fun getData(): Response<employee>

    @GET("users/3")
    suspend fun getData1(): Response<employee>

    companion object {
        fun getService(): ApiService {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val httpClient = OkHttpClient.Builder()
                .connectTimeout(2,TimeUnit.MINUTES)
                .callTimeout(2,TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .client(httpClient)
                .build()

            return retrofit.create(ApiService::class.java)

        }
    }

}