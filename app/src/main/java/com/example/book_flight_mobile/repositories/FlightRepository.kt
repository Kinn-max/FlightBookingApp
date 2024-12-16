package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse

interface FlightRepository {
    suspend fun loadFlightHome(): List<FlightResponse>
    suspend fun searchFlight( flightRequest: FlightRequest): List<FlightResponse>
}