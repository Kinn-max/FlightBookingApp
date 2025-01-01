package com.example.book_flight_mobile.repositories

import com.example.book_flight_mobile.models.TicketRequest

interface PaymentRepository {
    suspend fun createPaymentVnPay( ticketRequest: TicketRequest): String
}