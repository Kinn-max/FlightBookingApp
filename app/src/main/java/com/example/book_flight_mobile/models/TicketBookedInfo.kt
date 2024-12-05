package com.example.book_flight_mobile.models

import java.util.Date

data class TicketBookedInfo(
    val ticketId: Long,
    val price: Double,
    val seatNumber: String,
    val flightCode: String,
    val arrivalTime: Date,
    val departureTime: Date,
    val arrivalAirportName: String,
    val departureAirportName: String,
    val airlineName: String,
    val luggage: Double
)
