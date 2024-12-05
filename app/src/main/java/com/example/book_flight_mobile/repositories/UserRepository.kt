package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.UserResponse

interface UserRepository {
    suspend fun loadUserInfo(): UserResponse
}