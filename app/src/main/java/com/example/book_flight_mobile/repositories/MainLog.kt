package com.example.book_flight_mobile.repositories

interface MainLog {
    fun i(tag: String, msg:String)
    fun d(tag: String, msg:String)
    fun e(tag: String, msg:String)
}