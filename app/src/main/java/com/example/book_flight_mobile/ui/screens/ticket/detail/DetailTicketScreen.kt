package com.example.book_flight_mobile.ui.screens.ticket.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.ui.screens.profile.ProfileModelView
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTicketScreen(
    navController: NavHostController,
    viewModel: ProfileModelView,
    mainViewModel: MainViewModel,
    id: Long
) {
    val state = viewModel.uiState.collectAsState()
    // fix sau
    LaunchedEffect(Unit) {
        viewModel.ticketList()
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
        val ticket = state.value.ticketList.find { it.ticketId == id }
        if (ticket != null) {
            Column(modifier = Modifier.padding(padding)) {
                Text(
                    text = "Mã chuyến bay: ${ticket.flightCode}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Sân bay khởi hành: ${ticket.departureAirportName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Sân bay đến: ${ticket.arrivalAirportName}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Giá vé: ${ticket.price}",
                    style = MaterialTheme.typography.bodyMedium
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
}
