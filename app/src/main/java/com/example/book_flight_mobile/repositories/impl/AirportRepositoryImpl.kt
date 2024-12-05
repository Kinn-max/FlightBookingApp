package com.example.book_flight_mobile.repositories.impl

import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.models.AirportResponse
import com.example.book_flight_mobile.repositories.AirportRepository
import com.example.book_flight_mobile.repositories.MainLog
import javax.inject.Inject



class AirportRepositoryImpl @Inject constructor(
    private val log: MainLog?
) : AirportRepository {
    override suspend fun loadAirport(): List<AirportResponse> {
        return try {
            val airports = RetrofitInstance.api.loadAirports()
            log?.d("AirportRepository", "Retrieved ${airports.size} airports")
            airports
        } catch (e: Exception) {
            log?.e("AirportRepository", "Error loading airports: ${e.message}")
            emptyList()
        }
    }
}

