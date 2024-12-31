package com.example.book_flight_mobile.api

import com.example.book_flight_mobile.models.AirportResponse
import com.example.book_flight_mobile.models.AuthResponse
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.models.HomeResponse
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.models.UserLogin
import com.example.book_flight_mobile.models.UserRegister
import com.example.book_flight_mobile.models.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import javax.validation.Valid

interface ApiService {
    @GET( "api/flight/by-user")
    suspend fun loadFlights(): List<FlightResponse>

    @GET("api/home")
    suspend fun loadHomeSearch(): HomeResponse

    @GET("api/ticket")
    suspend fun loadTicketInfoList(): List<TicketBookedInfo>

    @GET("/api/user/profile")
    suspend fun loadUserInfo(): UserResponse

    @Headers("Content-Type: application/json")
    @POST("api/flight/search")
    suspend fun searchFlight(@Body  flightRequest: FlightRequest): List<FlightResponse>

    @GET("api/flight/detail/{id}")
    suspend fun getFlightById(@Path("id") id: Long): FlightResponse

    @POST("api/auth/login")
    suspend fun login(@Body userLogin: UserLogin): AuthResponse

    @POST("/api/user/register")
    suspend fun register(userRegister: UserRegister)

    @GET("api/ticket/by-user")
    suspend fun  getDetailTicketByUser():TicketBookedInfo

    @GET("api/ticket/{id}")
    suspend fun  getDetailTicketById(@Path("id") id: Long):TicketBookedInfo
}