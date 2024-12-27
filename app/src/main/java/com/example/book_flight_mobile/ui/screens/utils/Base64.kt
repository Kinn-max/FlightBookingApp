package com.example.book_flight_mobile.ui.screens.utils
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


fun extractBase64Data(base64String: String): String {
    return if (base64String.startsWith("data:image")) {
        base64String.substringAfter(",")
    } else {
        base64String
    }
}

fun base64ToBitmap(base64String: String): Bitmap? {
    return try {
        val pureBase64 = extractBase64Data(base64String)
        val decodedBytes = Base64.decode(pureBase64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}