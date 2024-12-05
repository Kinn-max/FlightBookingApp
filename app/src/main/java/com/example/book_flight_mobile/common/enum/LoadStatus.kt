package com.example.book_flight_mobile.common.enum

sealed class LoadStatus(val description: String= ""){
    class Innit():LoadStatus()
    class Loading():LoadStatus()
    class Success:LoadStatus()
    class Error(val error:String):LoadStatus(error)
}