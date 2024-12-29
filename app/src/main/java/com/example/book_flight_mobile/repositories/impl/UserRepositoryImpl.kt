package com.example.book_flight_mobile.repositories.impl

import android.annotation.SuppressLint
import coil.network.HttpException
import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.models.AuthResponse
import com.example.book_flight_mobile.models.UserLogin
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


//        return UserResponse(
//            id = 1L,
//            fullName = "Nguyen Van A",
//            phoneNumber = "0123456789",
//            address = "123 Le Loi, Hanoi, Vietnam",
//            dateOfBirth = LocalDate.of(1990, 5, 20)
//        )
       return null
    }

    override suspend fun login(userLogin: UserLogin): AuthResponse {
        return try {
            val hi = UserLogin(email = "kinmax200418@gmail.com", password = "123")
            val token = RetrofitInstance.api.login(hi)
            log?.d("UserLogin", "Retrieved user${token.user}")
            token
        } catch (e: Exception) {
            log?.e("UserLogin", "Error loading: ${e.message}")
            throw e
        }
    }

    override suspend fun register(userRegister: UserRegister) {
        TODO("Not yet implemented")
    }
}