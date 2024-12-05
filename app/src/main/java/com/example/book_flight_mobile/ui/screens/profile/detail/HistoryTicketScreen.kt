package com.example.book_flight_mobile.ui.screens.profile.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.ui.screens.profile.ProfileModelView
import com.example.book_flight_mobile.ui.screens.search.SearchModelView

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
        println("Current state: ${state.ticketList}")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Tìm kiếm chuyến bay") })
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
            } else if (state.status is LoadStatus.Error) {
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
            } else {
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
                        state.ticketList.forEach { ticket ->
                            Text("Mã chuyến bay: ${ticket.flightCode}")
                            Text("Sân bay khởi hành: ${ticket.departureAirportName}")
                            Text("Sân bay đến: ${ticket.arrivalAirportName}")
                            Text("Giá vé: ${ticket.price}")
                        }
                    }
                }
            }

        }
    }
}
