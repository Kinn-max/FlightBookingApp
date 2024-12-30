package com.example.book_flight_mobile.ui.screens.utils

import java.text.SimpleDateFormat
import java.util.Locale
fun reformatDate(inputDate: String): String {
    val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    val date = inputFormat.parse(inputDate)
    return outputFormat.format(date!!)
}
