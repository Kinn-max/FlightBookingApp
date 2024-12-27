package com.example.book_flight_mobile.ui.screens.ticket.detail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
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
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.ui.screens.profile.ProfileModelView
import com.example.book_flight_mobile.ui.screens.search.listsearch.CustomTopBarSearch
import com.example.book_flight_mobile.ui.screens.ticket.TicketModelView
import com.example.book_flight_mobile.ui.screens.utils.CardLoading

@Composable
fun DetailTicketScreen(
    navController: NavHostController,
    viewModel: TicketModelView,
    mainViewModel: MainViewModel,
    id: Long
) {
    val state = viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    LaunchedEffect(id) {
        viewModel.getTicketById(id)
    }
    Scaffold(
        topBar = {
            CustomTopBarSearch(
                title = if (state.value.ticket != null) {
                    "${state.value.ticket?.departureAirportName} - ${state.value.ticket?.arrivalAirportName}"
                } else {
                    "Điểm đi - Điểm đến"
                },
                navController = navController
            )
        }
    ) { padding ->
        val ticket = state.value.ticket
        when (state.value.status) {
            is LoadStatus.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is LoadStatus.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.value.status.description,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                }
                mainViewModel.setError(state.value.status.description)
                viewModel.reset()
            }

            is LoadStatus.Success -> {
                if (ticket != null) {
                    Column(modifier = Modifier.padding(padding),) {
                        TicketDetailContent(ticket,scrollState)
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
            else -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Đang tải dữ liệu...",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
@Composable
fun TicketDetailContent( ticket: TicketBookedInfo,scrollState: ScrollState) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Chuyến đi: Hồ Chí Minh - Hà Nội",
                style = TextStyle(
                    color = Color(0xFF27272A),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 24.sp,
                    textAlign = TextAlign.Left,
                )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TicketCardDetail(ticket)
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .background(Color(0xFFFFFFFF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Chi tiết giá",
                        style = TextStyle(
                            color = Color(0xFF27272A),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp,
                            textAlign = TextAlign.Left,
                        )
                    )

                }
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
                        Text(
                            "Tổng tiền: ",
                            style = TextStyle(
                                fontWeight = FontWeight.W700,
                                fontSize = 16.sp,
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
                            fontSize = 16.sp,
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
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .background(Color(0xFFFFFFFF))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Thông tin liên hệ",
                        style = TextStyle(
                            color = Color(0xFF27272A),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 24.sp,
                            textAlign = TextAlign.Left,
                        )
                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            "Họ và tên",
                            style = TextStyle(
                                fontWeight = FontWeight.W700,
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
                            text = "Trần Chung Kiên",
                            style = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                lineHeight = 21.sp,
                                color = Color(0xFF27272A)
                            ),
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
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            "Số điện thoại",
                            style = TextStyle(
                                fontWeight = FontWeight.W700,
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
                            text = "64564565646",
                            style = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 14.sp,
                                lineHeight = 21.sp,
                                color = Color(0xFF27272A)
                            ),
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
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            "Email",
                            style = TextStyle(
                                fontWeight = FontWeight.W700,
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
                            text = "kien2004@gmail.com",
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
    }
}
@Composable
fun TicketCardDetail(ticket: TicketBookedInfo) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                0xFFFFFFFF
            )
        ),
        elevation = CardDefaults.cardElevation(1.dp),
    )  {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
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
                                ticket.departureAirportName,
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
                            ).padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                            Text(
                                ticket.departureAirportName,
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
                                    ,
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Divider(
                                    modifier = Modifier
                                        .weight(0.6f)
                                        .height(2.dp)
                                        .background(Color(0xFF515158))
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowRight,
                                    contentDescription = "Arrow",
                                    modifier = Modifier
                                        .size(12.dp)
                                        .padding(start = (0).dp)
                                        .align(Alignment.CenterVertically)
                                        .offset(x = (-6).dp)
                                    ,tint = Color(0xFF515158)
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
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp)
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
                            ).padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color(0xFFF0F8FF)
                            ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ){
                                Column(
                                    modifier = Modifier.weight(1f) .padding(12.dp),
                                    horizontalAlignment = Alignment.Start
                                ){
                                    Text(
                                        "Vị trí ngồi ",
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 8.dp, end = 16.dp),
                                        style = TextStyle(
                                            fontWeight = FontWeight.W400,
                                            fontSize = 16.sp,
                                            lineHeight = 21.sp,
                                            color = Color(0xFF27272A)
                                        ),
                                    )
                                    Text(
                                        ticket.seatNumber,
                                        style = TextStyle(
                                            fontWeight = FontWeight.W700,
                                            fontSize = 16.sp,
                                            lineHeight = 21.sp,
                                            color = Color(0xFF27272A)
                                        ),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(start = 8.dp),
                                    )

                                }
                                Column(
                                    modifier = Modifier.weight(1f).padding(16.dp),
                                    horizontalAlignment = Alignment.End
                                ){
                                    Icon(
                                        imageVector = Icons.Default.FileCopy,
                                        contentDescription = "Departure",
                                        tint =Color(0xFF1A94FF),
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }

                        }

                    }
                }

            }
        }
    }
}
