package com.example.book_flight_mobile.models


data class SeatResponse(
    val id: Long,
    val seatNumber: String,
    val seatClass: String,
    val available: Boolean,
    val price: Double
)
