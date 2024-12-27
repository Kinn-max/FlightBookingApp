package com.example.book_flight_mobile.repositories.impl

import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.api.ApiService
import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.PlaneResponse
import com.example.book_flight_mobile.models.SeatResponse
import com.example.book_flight_mobile.repositories.FlightRepository
import com.example.book_flight_mobile.repositories.MainLog
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject

class FlightRepositoryImpl @Inject constructor(
    private val log: MainLog?
) : FlightRepository  {
    override suspend fun loadFlightHome(): List<FlightResponse> {
        delay(1000)
        return emptyList()
    }

    override suspend fun searchFlight( flightRequest: FlightRequest): List<FlightResponse> {
        delay(1000)
        return try {
            val flights = RetrofitInstance.api.loadFlights()
            log?.d("FlightRepository", "Retrieved ${flights.size} flights")
            flights
        } catch (e: Exception) {
            log?.e("FlightRepository", "Error loading flight: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getFlightById(id: Long): FlightResponse? {
        delay(500)
        return try {
            val flight = RetrofitInstance.api.getFlightById(id)
            log?.d("FlightRepository", "Retrieved flight")
            flight
        } catch (e: Exception) {
            log?.e("FlightRepository", "Error loading flight: ${e.message}")
            null
        }
    }


}