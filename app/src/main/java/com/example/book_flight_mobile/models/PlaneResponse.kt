package com.example.book_flight_mobile.models


data class PlaneResponse(
    val id: Long,
    val ecoClass: Int,
    val busClass: Int,
    val name: String,
    val nameAirline: String
)
