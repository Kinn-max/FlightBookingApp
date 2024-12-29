package com.example.book_flight_mobile.models

data class AuthResponse(
    val token: String,
    val user: User
)
data class User(
    val name: String,
    val email: String,
    val role: String
)