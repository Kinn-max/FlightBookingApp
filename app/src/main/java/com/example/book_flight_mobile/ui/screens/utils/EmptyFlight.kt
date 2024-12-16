package com.example.book_flight_mobile.ui.screens.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.book_flight_mobile.R

@Composable
fun EmptyFlight() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.plane),
            contentDescription = "Empty flight",
            modifier = Modifier
                .size(160.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "Không tìm thấy chuyến bay",
            color = Color(0xFF27272A),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                lineHeight = 24.sp
            ),
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
        Text(
            text = "Xin vui lòng chọn tìm kiếm khác",
            color = Color(0xFF27272A),
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                lineHeight = 21.sp
            ),
            modifier = Modifier
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    }
}