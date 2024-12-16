package com.example.book_flight_mobile.api

import com.example.book_flight_mobile.models.AirportResponse
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET( "flights")
    suspend fun loadFlights(): List<FlightResponse>

    @GET("api/airport")
    suspend fun loadAirports(): List<AirportResponse>

    @GET("api/ticket")
    suspend fun loadTicketInfoList(): List<TicketBookedInfo>

    @GET("api/user")
    suspend fun loadUserInfo(): UserResponse

    @POST("/api/flight/search")
    suspend fun searchFlight(@Body flightRequest: FlightRequest): List<FlightResponse>

}