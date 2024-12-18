package com.example.book_flight_mobile.repositories.impl

import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.api.ApiService
import com.example.book_flight_mobile.models.FlightRequest
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
        return listOf(
            FlightResponse(
                id = 1L,
                codeFlight = "VN123",
                departureTime = Date(2024, 12, 3, 8, 0), // Giả định thời gian khởi hành
                arrivalTime = Date(2024, 12, 3, 10, 0),  // Giả định thời gian hạ cánh
                departureAirport = "Noi Bai International Airport",
                departureLocation = "Hanoi, Vietnam",
                arrivalLocation = "Tan Son Nhat International Airport",
                arrivalAirport = "Ho Chi Minh City, Vietnam",
                codeDepartAirport = "HAN",
                codeArriAirport = "SGN",
                airline = "Vietnam Airlines",
                logoAirline = "https://example.com/logo/vietnam_airlines.png",
                ecoPrice = 150.0,
                busPrice = 250.0
            ),
            FlightResponse(
                id = 2L,
                codeFlight = "VN456",
                departureTime = Date(2024, 12, 4, 14, 30),
                arrivalTime = Date(2024, 12, 4, 16, 30),
                departureAirport = "Da Nang International Airport",
                departureLocation = "Da Nang, Vietnam",
                arrivalLocation = "Noi Bai International Airport",
                arrivalAirport = "Hanoi, Vietnam",
                codeDepartAirport = "DAD",
                codeArriAirport = "HAN",
                airline = "Bamboo Airways",
                logoAirline = "https://example.com/logo/bamboo_airways.png",
                ecoPrice = 100.0,
                busPrice = 200.0
            )
        )
    }

    override suspend fun searchFlight( flightRequest: FlightRequest): List<FlightResponse> {
        delay(1000)
        return listOf(
            FlightResponse(
                id = 1L,
                codeFlight = "VN123",
                departureTime = Date(2024, 12, 3, 8, 0), // Giả định thời gian khởi hành
                arrivalTime = Date(2024, 12, 3, 10, 0),  // Giả định thời gian hạ cánh
                departureAirport = "Noi Bai International Airport",
                departureLocation = "Hanoi, Vietnam",
                arrivalLocation = "Tan Son Nhat International Airport",
                arrivalAirport = "Ho Chi Minh City, Vietnam",
                codeDepartAirport = "HAN",
                codeArriAirport = "SGN",
                airline = "Vietnam Airlines",
                logoAirline = "https://example.com/logo/vietnam_airlines.png",
                ecoPrice = 150.0,
                busPrice = 250.0
            ),
            FlightResponse(
                id = 2L,
                codeFlight = "VN456",
                departureTime = Date(2024, 12, 4, 14, 30),
                arrivalTime = Date(2024, 12, 4, 16, 30),
                departureAirport = "Da Nang International Airport",
                departureLocation = "Da Nang, Vietnam",
                arrivalLocation = "Noi Bai International Airport",
                arrivalAirport = "Hanoi, Vietnam",
                codeDepartAirport = "DAD",
                codeArriAirport = "HAN",
                airline = "Bamboo Airways",
                logoAirline = "https://example.com/logo/bamboo_airways.png",
                ecoPrice = 100.0,
                busPrice = 200.0
            )
        )
    }

    override suspend fun getFlightById(id: Long): FlightResponse {
       delay(500)
        return FlightResponse(
            id = 2L,
            codeFlight = "VN456",
            departureTime = Date(2024, 12, 4, 14, 30),
            arrivalTime = Date(2024, 12, 4, 16, 30),
            departureAirport = "Da Nang International Airport",
            departureLocation = "Da Nang, Vietnam",
            arrivalLocation = "Noi Bai International Airport",
            arrivalAirport = "Hanoi, Vietnam",
            codeDepartAirport = "DAD",
            codeArriAirport = "HAN",
            airline = "Bamboo Airways",
            logoAirline = "https://example.com/logo/bamboo_airways.png",
            ecoPrice = 100.0,
            busPrice = 200.0
        )
    }


}