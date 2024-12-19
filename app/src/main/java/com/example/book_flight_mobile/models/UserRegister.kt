package com.example.book_flight_mobile.models

import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty

data class UserRegister(
    @field:NotEmpty(message = "Name cannot empty")
    @JsonProperty("full_name")
    val fullName: String,

    @field:NotEmpty(message = "Password cannot empty")
    val password: String,

    @field:NotEmpty(message = "Email cannot empty")
    @field:Email(message = "Invalid email")
    val email: String,

    @field:JsonProperty("phone_number")
    @field:NotEmpty(message = "Phone number cannot empty")
    val phoneNumber: String,

    @field:JsonProperty("date_of_birth")
    val dateOfBirth: String? = null,

    val address: String? = null
)
