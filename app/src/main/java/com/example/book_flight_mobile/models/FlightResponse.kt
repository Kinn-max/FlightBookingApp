package com.example.book_flight_mobile.models

import java.util.Date

data class FlightResponse(
    val id: Long,
    val codeFlight: String,
    val departureTime: Date,
    val arrivalTime: Date,
    val departureAirport: String,
    val departureLocation: String,
    val arrivalLocation: String,
    val arrivalAirport: String,
    val codeDepartAirport: String,
    val codeArriAirport: String,
    val airline: String,
    val logoAirline: String,
    val ecoPrice: Double,
    val busPrice: Double
)
