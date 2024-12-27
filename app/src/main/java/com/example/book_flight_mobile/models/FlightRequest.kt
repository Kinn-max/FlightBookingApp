package com.example.book_flight_mobile.models


import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotNull

data class FlightRequest(
    @field:NotNull
    @field:JsonProperty("departure_airport")
    val departureAirport: Long,

    @field:NotNull
    @field:JsonProperty("arrival_airport")
    val arrivalAirport: Long,

    @field:NotNull
    @field:JsonProperty("departure_time")
    val departureTime: String,

    @field:NotNull
    @field:JsonProperty("seat_class")
    val seatClass: String
)

