package com.example.book_flight_mobile.repositories.impl

import android.annotation.SuppressLint
import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.models.UserLogin
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.TicketRepository
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.util.Date
import javax.inject.Inject

class TicketRepositoryImpl @Inject constructor(
    private val log: MainLog?
):TicketRepository{
//    @SuppressLint("NewApi")
//    override suspend fun loadTicketInfoList(): List<TicketBookedInfo> {
//        delay(1000)
//        return listOf(
//            TicketBookedInfo(
//                ticketId = 1L,
//                price = 150.0,
//                seatNumber = "12A",
//                createAt = LocalDate.of(2024, 12, 1),
//                email = "john.doe@example.com",
//                name = "John Doe",
//                phone = "0123456789",
//                seatClass = "Economy",
//                logo = "https://linktoairlinelogo.com",
//                flightCode = "VN123",
//                arrivalTime = Date(2024, 12, 6, 10, 30),
//                departureTime = Date(2024, 12, 6, 8, 0),
//                arrivalAirportName = "Hà Nội",
//                departureAirportName = "Hồ Chí Minh",
//                airlineName = "Vietnam Airlines",
//                luggage = 20.0
//            ),
//            TicketBookedInfo(
//                ticketId = 2L,
//                price = 200.0,
//                seatNumber = "15B",
//                createAt = LocalDate.of(2024, 12, 2),
//                email = "jane.smith@example.com",
//                name = "Jane Smith",
//                phone = "0987654321",
//                seatClass = "Business",
//                logo = "https://linktoairlinelogo.com",
//                flightCode = "VN124",
//                arrivalTime = Date(2024, 12, 7, 11, 30),
//                departureTime = Date(2024, 12, 7, 9, 0),
//                arrivalAirportName = "Hà Nội",
//                departureAirportName = "Hồ Chí Minh",
//                airlineName = "Vietnam Airlines",
//                luggage = 25.0
//            )
//        )
//    }

    override suspend fun getTicketDetailByUser(): TicketBookedInfo? {
        return try {
            val ticketDetail = RetrofitInstance.api.getDetailTicketByUser()
            log?.d("Ticket ", "Retrieved ticket${ticketDetail}")
            ticketDetail
        } catch (e: Exception) {
            log?.e("Ticket", "Error loading: ${e.message}")
            throw e
        }
    }

    override suspend fun getDetailTicketById(id: Long): TicketBookedInfo {
        return try {
            val ticketDetail = RetrofitInstance.api.getDetailTicketById(id)
            log?.d("Ticket id", "Retrieved ticket")
            ticketDetail
        } catch (e: Exception) {
            log?.e("Ticket", "Error loading: ${e.message}")
            throw e
        }
    }

}