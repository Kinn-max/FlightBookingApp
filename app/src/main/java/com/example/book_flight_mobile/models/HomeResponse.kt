package com.example.book_flight_mobile.models

data class  HomeResponse(
    val airportResponses:List<AirportResponse>,
    val seatClasses:List<String>
)