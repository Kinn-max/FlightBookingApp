package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import retrofit2.http.Headers
import retrofit2.http.POST

interface FlightRepository {
    suspend fun loadFlightHome(): List<FlightResponse>
    suspend fun searchFlight( flightRequest: FlightRequest): List<FlightResponse>
    suspend fun getFlightById(id:Long): FlightResponse?
}