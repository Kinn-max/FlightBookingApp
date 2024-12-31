package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.TicketBookedInfo
import retrofit2.http.Path

interface TicketRepository {
//    suspend fun loadTicketInfoList(): List<TicketBookedInfo>
    suspend fun  getTicketDetailByUser():TicketBookedInfo?
    suspend fun  getDetailTicketById(id: Long):TicketBookedInfo
}