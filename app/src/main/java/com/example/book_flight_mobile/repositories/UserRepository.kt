package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.AuthResponse
import com.example.book_flight_mobile.models.UserLogin
import com.example.book_flight_mobile.models.UserRegister
import com.example.book_flight_mobile.models.UserResponse
import retrofit2.http.Body

interface UserRepository {
    suspend fun loadUserInfo(): UserResponse?
    suspend fun login(@Body userLogin: UserLogin):AuthResponse
    suspend fun register(userRegister: UserRegister)
}