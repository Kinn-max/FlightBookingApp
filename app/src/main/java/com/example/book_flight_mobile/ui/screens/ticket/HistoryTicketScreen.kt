package com.example.book_flight_mobile.ui.screens.ticket

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.ui.screens.profile.ProfileModelView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTicketScreen(
    navController: NavHostController,
    viewModel: ProfileModelView,
    mainViewModel: MainViewModel
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.ticketList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Lịch sử đặt vé") },
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
        Column(modifier = Modifier.padding(padding)) {
            if (state.status is LoadStatus.Loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else if (state.status is LoadStatus.Error) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Error: ${state.status.description}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                mainViewModel.setError(state.status.description)
                viewModel.reset()
            }
            else {
                if (state.ticketList.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Không có vé nào được đặt.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (state.ticketList.isEmpty()) {
                            Text("Không có vé nào được đặt.", style = MaterialTheme.typography.bodyLarge)
                        } else {
                            state.ticketList.forEach { ticket ->
                                Card(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate("${Screen.TicketHistoryDetail.route}?id=${ticket.ticketId}")
                                        }
                                ) {
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween,  modifier = Modifier .padding(bottom = 8.dp)) {
                                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                                                Icon(
                                                    imageVector = Icons.Default.LocationOn,
                                                    contentDescription = "Departure Location",
                                                    modifier = Modifier.size(24.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(text = "${ticket.departureAirportName}")
                                            }
                                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                                                Icon(
                                                    imageVector = Icons.Default.ArrowRightAlt,
                                                    contentDescription = "Arrow Right",
                                                    modifier = Modifier.size(24.dp)
                                                )
                                            }
                                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                                                Icon(
                                                    imageVector = Icons.Default.LocationOn,
                                                    contentDescription = "Arrival Location",
                                                    modifier = Modifier.size(24.dp)
                                                )
                                                Spacer(modifier = Modifier.width(4.dp))
                                                Text(text = "${ticket.arrivalAirportName}")
                                            }
                                        }
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .then(
                                                    Modifier.drawWithContent{
                                                        drawContent()
                                                        drawLine(
                                                            color = Color.Black,
                                                            strokeWidth = 1.dp.toPx(),
                                                            start = Offset(0f, 0f),
                                                            end = Offset(size.width, 0f)
                                                        )
                                                    }
                                                ).padding(top = 8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,

                                        ) {
                                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                                                Text(
                                                    text = "Mã đơn hàng: ${ticket.price}",
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }

                                            Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                                                Text(
                                                    text = "${ticket.price}",
                                                    style = MaterialTheme.typography.bodyLarge.copy(
                                                        fontWeight = FontWeight.Bold,
                                                        color = MaterialTheme.colorScheme.primary,
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
            }
        }
    }
}
