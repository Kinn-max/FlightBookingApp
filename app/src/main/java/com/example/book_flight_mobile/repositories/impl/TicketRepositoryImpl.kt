package com.example.book_flight_mobile.repositories.impl

import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.TicketRepository
import kotlinx.coroutines.delay
import java.util.Date
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val log: MainLog?
):TicketRepository{
    override suspend fun loadTicketInfoList(): List<TicketBookedInfo> {
        delay(1000)
        return listOf(
            TicketBookedInfo(
                ticketId = 1L,
                price = 150.0,
                seatNumber = "12A",
                flightCode = "VN123",
                arrivalTime = Date(2024, 12, 6, 10, 30),
                departureTime = Date(2024, 12, 6, 8, 0),
                arrivalAirportName = "Hà nội",
                departureAirportName = "Hồ Chí Minh",
                airlineName = "Vietnam Airlines",
                luggage = 20.0
            ),
            TicketBookedInfo(
                ticketId = 2L,
                price = 200.0,
                seatNumber = "14B",
                flightCode = "VN456",
                arrivalTime = Date(2024, 12, 7, 12, 0),
                departureTime = Date(2024, 12, 7, 9, 30),
                arrivalAirportName = "Hà nội",
                departureAirportName = "Hồ Chí Minh",
                airlineName = "VietJet Air",
                luggage = 15.0
            ),
            TicketBookedInfo(
                ticketId = 3L,
                price = 120.0,
                seatNumber = "8C",
                flightCode = "VN789",
                arrivalTime = Date(2024, 12, 8, 11, 45),
                departureTime = Date(2024, 12, 8, 9, 15),
                arrivalAirportName = "Hà nội",
                departureAirportName = "Hồ Chí Minh",
                airlineName = "Bamboo Airways",
                luggage = 10.0
            )
        )
    }

    override suspend fun getTicketById(id: Long): TicketBookedInfo {
        delay(1000)
        return TicketBookedInfo(
                ticketId = 1L,
                price = 150.0,
                seatNumber = "12A",
                flightCode = "VN123",
                arrivalTime = Date(2024, 12, 6, 10, 30),
                departureTime = Date(2024, 12, 6, 8, 0),
                arrivalAirportName = "Hà nội",
                departureAirportName = "Hồ Chí Minh",
                airlineName = "Vietnam Airlines",
                luggage = 20.0
        )
    }

}