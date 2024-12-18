package com.example.book_flight_mobile.ui.screens.ticket.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.R
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.models.UserResponse
import com.example.book_flight_mobile.ui.screens.search.DatePickerDialog
import com.example.book_flight_mobile.ui.screens.search.SelectDropdown
import com.example.book_flight_mobile.ui.screens.search.convertMillisToDate
import com.example.book_flight_mobile.ui.screens.search.listsearch.CustomTopBarSearch
import com.example.book_flight_mobile.ui.screens.search.listsearch.FlightCardDetail
import com.example.book_flight_mobile.ui.screens.ticket.TicketModelView
import okhttp3.internal.userAgent


@Composable
fun SummaryTicketScreen(
    navController: NavHostController,
    viewModel: TicketModelView,
    mainViewModel: MainViewModel,
    id: Long
) {
    LaunchedEffect(Unit) {
        viewModel.getFlightById(id)
        viewModel.getUser()
    }
    val availableSeats = listOf("Business", "Economy")
    val availableLuggage = listOf("10kg - 100.000VND", "20kg - 200.000VND")
    var selectedSeat by remember { mutableStateOf(availableSeats[0]) }
    var selectedLuggage by remember { mutableStateOf(availableLuggage[0]) }
    val state = viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            CustomTopBarSearch("Tóm tắt đặt chỗ", navController)
        }
    ) { padding ->
        when (state.value.status) {
            is LoadStatus.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is LoadStatus.Success -> {
                if (state.value.flight != null) {
                    Column(modifier = Modifier.padding(padding)) {
                        FlightDetailSummary(
                            user = state.value.user!!,
                            fromTo = "Hồ Chí Minh - Hà Nội",
                            flightResponse = state.value.flight!!,
                            availableSeats = availableSeats,
                            availableLuggage = availableLuggage,
                            selectedSeat = selectedSeat,
                            selectedLuggage = selectedLuggage,
                            onSeatSelected = { selectedSeat = it },
                            onLuggageSelected = { selectedLuggage = it
                            }
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Không tìm thấy vé.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
            is LoadStatus.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Đã xảy ra lỗi, vui lòng thử lại sau.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Đang khởi tạo...",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }

}
@Composable
fun FlightDetailSummary(
    user:UserResponse,
    fromTo: String,
    flightResponse: FlightResponse,
    availableSeats: List<String>,
    availableLuggage: List<String>,
    selectedSeat: String,
    selectedLuggage: String,
    onSeatSelected: (String) -> Unit,
    onLuggageSelected: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chuyến đi: $fromTo",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Column() {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5FA)),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SelectDropdown(
                                label = "Loại vé",
                                options = availableSeats,
                                selectedOption = selectedSeat,
                                onOptionSelected = onSeatSelected
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            SelectDropdown(
                                label = "Hành lý",
                                options = availableLuggage,
                                selectedOption = selectedLuggage,
                                onOptionSelected = onLuggageSelected
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                value = user.fullName,
                                onValueChange = { },
                                label = { Text("Họ và tên") },
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                            )

                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                value = user.phoneNumber,
                                onValueChange = { },
                                label = { Text("Số điện thoại") },
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            OutlinedTextField(
                                value = user.address,
                                onValueChange = { },
                                label = { Text("Email") },
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(64.dp)
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth()
                                .then(
                                    Modifier.drawWithContent {
                                        drawContent()
                                        drawLine(
                                            color = Color(0xFFEBEBF0),
                                            strokeWidth = 1.dp.toPx(),
                                            start = Offset(0f, 0f),
                                            end = Offset(size.width, 0f)
                                        )
                                    }
                                ).padding(top = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.vnpay),
                                        contentDescription = "Airline logo",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .padding(end = 8.dp)
                                    )
                                    Column(
                                        modifier = Modifier.weight(1f),
                                        horizontalAlignment = Alignment.Start
                                    ) {
                                        Text(
                                            "Phương thức thanh toán",
                                            style = TextStyle(
                                                color = Color(0xFF27272A),
                                                fontWeight = FontWeight.W500,
                                                fontSize = 16.sp,
                                                lineHeight = 18.sp
                                            )
                                        )

                                    }
                                }

                            }
                        }

                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFFFFFFF))
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, top = 20.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                "Tổng tiền: ",
                                style = TextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 18.sp,
                                    lineHeight = 21.sp,
                                    color = Color(0xFF27272A)
                                ),
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "2.000.000 đ",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily.SansSerif,
                                lineHeight = 21.sp,
                                color = Color(0xFF1A94FF),
                                textAlign = TextAlign.Right,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
            Button(
                onClick = {
                    // Handle button click action here
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(8.dp)
                    .background(Color(0xFF1A94FF), shape = RoundedCornerShape(4.dp)),
                contentPadding = PaddingValues(8.dp)
            )
            {
                Text(
                text = "Thanh toán",
                color = Color.White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            }
        }

    }
}


