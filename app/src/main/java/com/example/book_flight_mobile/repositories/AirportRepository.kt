package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.AirportResponse

interface AirportRepository {
    suspend fun loadAirport(): List<AirportResponse>
}