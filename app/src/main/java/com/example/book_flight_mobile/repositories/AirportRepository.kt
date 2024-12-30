package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.AirportResponse
import com.example.book_flight_mobile.models.HomeResponse

interface AirportRepository {
    suspend fun getHomeSearch(): HomeResponse?
}