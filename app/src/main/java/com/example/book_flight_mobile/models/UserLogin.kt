package com.example.book_flight_mobile.models

import javax.validation.constraints.NotEmpty

data class UserLogin(
    @field:NotEmpty(message = "Email cannot empty")
    val email: String,

    @field:NotEmpty(message = "Password cannot empty")
    val password: String
)
