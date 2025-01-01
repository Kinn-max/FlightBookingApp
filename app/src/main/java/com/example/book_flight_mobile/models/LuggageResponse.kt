package com.example.book_flight_mobile.models

data class LuggageResponse(
    val id: Long,
    val luggageType: String,
    val weight: Double,
    val price: Double,
)