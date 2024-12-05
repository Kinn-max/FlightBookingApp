package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.TicketBookedInfo

interface TicketRepository {
    suspend fun loadTicketInfoList(): List<TicketBookedInfo>
}