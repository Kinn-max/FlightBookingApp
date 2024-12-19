package com.example.book_flight_mobile.ui.screens.ticket.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.R
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.ui.screens.profile.ProfileModelView
import com.example.book_flight_mobile.ui.screens.ticket.TicketModelView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTicketScreen(
    navController: NavHostController,
    viewModel: TicketModelView,
    mainViewModel: MainViewModel,
    id: Long
) {
    val state = viewModel.uiState.collectAsState()
    // fix sau
    LaunchedEffect(id) {
        viewModel.getTicketById(id)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Chi tiết vé ${id}") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        val ticket = state.value.ticket
        if (ticket != null) {
            Column(modifier = Modifier.padding(padding)) {
                TicketDetailContent(ticket)
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
}
@Composable
fun FlightCardShow(flight: FlightResponse) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                0xFFF5F5FA
            )
        ),
        elevation = CardDefaults.cardElevation(4.dp),
    )  {
        Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text("07:45",
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.W700,
                            fontSize = 18.sp,
                            lineHeight = 21.sp
                        ))
                    Text(flight.codeDepartAirport ,
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            lineHeight = 21.sp
                        )
                    )
                }

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "1g 15p",
                        color = Color(0xFF808089),
                        style = TextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            modifier = Modifier
                                .width(53.dp)
                                .height(2.dp)
                                .background(Color.Black)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(12.dp)
                                .padding(start = 0.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }

                    Text(
                        "Bay thẳng",
                        color = Color(0xFF808089),
                        style = TextStyle(
                            fontWeight = FontWeight.W400,
                            fontSize = 14.sp,
                            lineHeight = 18.sp
                        )
                    )
                }


                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text("09:00",
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.W700,
                            fontSize = 18.sp,
                            lineHeight = 21.sp
                        ))
                    Text(flight.codeArriAirport,
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            lineHeight = 21.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .then(
                        Modifier.drawWithContent{
                            drawContent()
                            drawLine(
                                color = Color(0xFFEBEBF0),
                                strokeWidth = 1.dp.toPx(),
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f)
                            )
                        }
                    ).padding(top = 8.dp),
            ) {
                Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vietjet),
                            contentDescription = "Airline logo",
                            modifier = Modifier
                                .size(40.dp)
                                .padding(end = 6.dp)
                        )
                        Text(
                            flight.airline,
                            style = TextStyle(
                                color = Color(0xFF27272A),
                                fontWeight = FontWeight.W500,
                                fontSize = 14.sp,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TicketDetailContent( ticket: TicketBookedInfo) {
    Column(
        modifier = Modifier
            .fillMaxHeight(0.6f)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chi tiết vé",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            TicketCardDetail(ticket)
            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    "Ghế thường",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
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
                                    text = "${ticket.price}",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        lineHeight = 21.sp,
                                        color = Color(0xFF27272A)
                                    ),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    "Ghế thương gia",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
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
                                    text = "${ticket.price}",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        lineHeight = 21.sp,
                                        color = Color(0xFF27272A)
                                    ),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    "Thuế",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
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
                                    text = "Đã bao gồm",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        lineHeight = 21.sp,
                                        color = Color(0xFF808089)
                                    ),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    "Hành lý",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
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
                                    text = "0 đ",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        lineHeight = 21.sp,
                                        color = Color(0xFF27272A)
                                    ),
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.Start
                            ) {
                                Text(
                                    "Giảm giá",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
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
                                    text = "0 đ",
                                    style = TextStyle(
                                        fontWeight = FontWeight.W400,
                                        fontSize = 14.sp,
                                        lineHeight = 21.sp,
                                        color = Color(0xFF00AB56)
                                    ),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun TicketCardDetail(ticket: TicketBookedInfo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFEBEBF0), shape = RoundedCornerShape(8.dp))
            .border(BorderStroke(1.dp, Color(0xFFEBEBF0))),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.vietjet),
                            contentDescription = "Airline logo",
                            modifier = Modifier
                                .size(60.dp)
                                .padding(end = 6.dp)
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                ticket.arrivalAirportName,
                                style = TextStyle(
                                    color = Color(0xFF27272A),
                                    fontWeight = FontWeight.W500,
                                    fontSize = 16.sp,
                                    lineHeight = 18.sp
                                )
                            )
                            Text(
                                text = "VN-158",
                                style = TextStyle(
                                    color = Color(0xFF808089),
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 12.sp,
                                    lineHeight = 18.sp
                                )
                            )

                        }
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
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                            Text(
                                ticket.arrivalAirportName,
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W700,
                                    fontSize = 18.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            Text(
                                "07:45",
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            Text(
                                "T7, 05/02/2022",
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp
                                )
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "1g 15p",
                                color = Color(0xFF808089),
                                style = TextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    lineHeight = 18.sp
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Divider(
                                    modifier = Modifier
                                        .width(53.dp)
                                        .height(2.dp)
                                        .background(Color.Black)
                                )
                                Icon(
                                    imageVector = Icons.Default.ArrowForward,
                                    contentDescription = "Arrow",
                                    modifier = Modifier
                                        .size(12.dp)
                                        .padding(start = 0.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }

                            Text(
                                "Bay thẳng",
                                color = Color(0xFF808089),
                                style = TextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp,
                                    lineHeight = 18.sp
                                )
                            )
                        }


                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                            Text(
                                ticket.arrivalAirportName,
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W700,
                                    fontSize = 18.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            Text(
                                "07:45",
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            Text(
                                "T7, 05/02/2022",
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp
                                )
                            )
                        }
                    }
                }

            }
        }

    }
}
