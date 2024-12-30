package com.example.book_flight_mobile.api

import com.example.book_flight_mobile.config.AuthInterceptor
import com.example.book_flight_mobile.config.TokenManager
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private const val BASE_URL = "http://10.0.2.2:8081/"

    private lateinit var tokenManager: TokenManager

    fun initialize(tokenManager: TokenManager) {
        this.tokenManager = tokenManager
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor = AuthInterceptor(tokenManager)
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    val api: ApiService by lazy {
        val gson = GsonBuilder()
            .setLenient()
            .create()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService::class.java)
    }
}
