package com.example.book_flight_mobile.repositories.impl

import android.annotation.SuppressLint
import com.example.book_flight_mobile.models.UserRegister
import com.example.book_flight_mobile.models.UserResponse
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.UserRepository
import java.time.LocalDate
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val log: MainLog?
):UserRepository{
    @SuppressLint("NewApi")
    override suspend fun loadUserInfo(): UserResponse? {


        return UserResponse(
            id = 1L,
            fullName = "Nguyen Van A",
            phoneNumber = "0123456789",
            address = "123 Le Loi, Hanoi, Vietnam",
            dateOfBirth = LocalDate.of(1990, 5, 20)
        )
    }

    override suspend fun login(username: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun register(userRegister: UserRegister) {
        TODO("Not yet implemented")
    }
}