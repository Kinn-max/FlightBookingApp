package com.example.book_flight_mobile.config

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TokenManager @Inject constructor(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("auth_token", token)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString("auth_token", null)
    }

    fun clearToken() {
        val editor = sharedPreferences.edit()
        editor.remove("auth_token")
        editor.apply()
    }

    fun saveUserName(name: String) {
        val editor = sharedPreferences.edit()
        editor.putString("user_name", name)
        editor.apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("user_name", null)
    }
}
