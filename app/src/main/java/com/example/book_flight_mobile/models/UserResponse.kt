package com.example.book_flight_mobile.models

import java.time.LocalDate

data class UserResponse(
    val id: Long,
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val dateOfBirth:String,
    val bookingList: List<TicketBookedInfo>
)