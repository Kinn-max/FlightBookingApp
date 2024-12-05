package com.example.book_flight_mobile.repositories.impl

import com.example.book_flight_mobile.models.UserResponse
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val log: MainLog?
):UserRepository{
    override suspend fun loadUserInfo(): UserResponse {
        TODO("Not yet implemented")
    }
}