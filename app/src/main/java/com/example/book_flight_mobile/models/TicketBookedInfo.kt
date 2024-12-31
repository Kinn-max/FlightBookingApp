package com.example.book_flight_mobile.models

import java.time.LocalDate
import java.util.Date
import javax.validation.constraints.Email

data class TicketBookedInfo(
    val ticketId: Long,
    val price: Double,
    val seatNumber: String,
    val createAt: String,
    val email: String,
    val name: String,
    val phone: String,
    val seatClass:String,
    val logo:String,
    val flightCode: String,
    val arrivalTime: Date,
    val departureTime: Date,
    val arrivalAirportName: String,
    val departureAirportName: String,
    val airlineName: String,
    val luggage: Double
)
