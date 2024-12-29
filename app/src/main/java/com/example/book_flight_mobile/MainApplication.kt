package com.example.book_flight_mobile

import android.app.Application
import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.config.TokenManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val tokenManager = TokenManager(applicationContext)
        RetrofitInstance.initialize(tokenManager)
    }
}
