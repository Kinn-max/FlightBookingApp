package com.example.book_flight_mobile.repositories.impl

import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.models.TicketRequest
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.PaymentRepository
import okhttp3.ResponseBody
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val log: MainLog?
): PaymentRepository {
    override suspend fun createPaymentVnPay(ticketRequest: TicketRequest): String {
        return try {
            val response = RetrofitInstance.api.createPaymentVnPay(ticketRequest)
            val result = response.string()
            log?.d("Payment", "Retrieved payment $result")
            result
        } catch (e: Exception) {
            log?.e("Payment", "Error loading ${e.message}")
            throw e
        }
    }
}