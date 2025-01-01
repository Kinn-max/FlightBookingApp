package com.example.book_flight_mobile.models

import javax.validation.constraints.NotNull


data class TicketRequest(
    @field:NotNull(message = "Seat class not null")
    val seatCLass: String,

    @field:NotNull(message = "Flight not null")
    val flightId: Long,

    val luggageId: Long? = null,

    @field:NotNull(message = "Name not null")
    val name: String,

    @field:NotNull(message = "Phone not null")
    val phone: String,

    @field:NotNull(message = "Email not null")
    val email: String
)