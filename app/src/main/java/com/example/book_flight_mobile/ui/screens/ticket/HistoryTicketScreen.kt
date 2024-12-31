package com.example.book_flight_mobile.ui.screens.ticket

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.rememberModalBottomSheetState
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.ui.screens.profile.ProfileModelView
import com.example.book_flight_mobile.ui.screens.search.listsearch.CustomTopBarSearch
import com.example.book_flight_mobile.ui.screens.utils.CardLoading
import com.example.book_flight_mobile.ui.screens.utils.EmptyFlight
import java.text.NumberFormat
import java.util.Locale

@Composable
fun HistoryTicketScreen(
    navController: NavHostController,
    viewModel: ProfileModelView,
    mainViewModel: MainViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.personalInformation()
    }

    Scaffold(
        topBar = {
            CustomTopBarSearch("Vé của tôi", navController)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            when (uiState.status) {
                is LoadStatus.Loading -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(5) {
                            CardLoading()
                        }
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
                            text = uiState.status.description,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                    mainViewModel.setError(uiState.status.description)
                    viewModel.reset()
                }
                is LoadStatus.Success -> {
                    if (uiState.ticketList.isEmpty()) {
                        EmptyFlight()
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(uiState.ticketList) { ticket ->
                                SummaryTicketHistory(ticket,navController)
                            }
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

}
@Composable
fun SummaryTicketHistory(ticket: TicketBookedInfo, navController: NavHostController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(
                0xFFF5F5FA
            )
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            navController.navigate(Screen.TicketHistoryDetail.route + "?id=${ticket.ticketId}")
        }
    )  {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Departure",
                        tint = Color(0xFF1A94FF),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = ticket.departureAirportName,
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.W500,
                            fontSize = 16.sp,
                            lineHeight = 21.sp
                        )
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Divider(
                            modifier = Modifier
                                .weight(0.5f)
                                .height(2.dp)
                                .background(Color(0xFF515158))
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(12.dp)
                                .padding(start = 0.dp)
                                .align(Alignment.CenterVertically)
                                .offset(x = (-6).dp),
                            tint = Color(0xFF515158)
                        )
                    }
                }

                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Arrival",
                        tint = Color(0xFF1A94FF),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = ticket.arrivalAirportName,
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
                    .padding(top = 20.dp)
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
                    )
                    .padding(top = 10.dp),
            ) {
                Column(
                    modifier = Modifier.weight(2f),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Mã chuyến bay: ",
                            style = TextStyle(
                                fontWeight = FontWeight.W400,
                                fontSize = 16.sp,
                                lineHeight = 21.sp,
                                color = Color(0xFF27272A)
                            ),
                        )
                        Text(
                            ticket.flightCode,
                            style = TextStyle(
                                fontWeight = FontWeight.W700,
                                fontSize = 16.sp,
                                lineHeight = 21.sp,
                                color = Color(0xFF27272A)
                            ),
                        )
                    }
                }
                Column(
                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.End
                ) {
                    val formattedPrice =
                        NumberFormat.getNumberInstance(Locale("vi", "VN")).format(ticket.price)
                    Text(
                        text = "${formattedPrice} đ",
                        style = TextStyle(
                            color = Color(0xFF1A94FF),
                            fontFamily = FontFamily.Default,
                            fontWeight = FontWeight.W700,
                            fontSize = 18.sp,
                            lineHeight = 21.sp
                        )
                    )
                }
            }
        }

    }
}


