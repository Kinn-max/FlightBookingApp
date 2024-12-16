package com.example.book_flight_mobile.ui.screens.search

import android.annotation.SuppressLint
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.EventSeat
import androidx.compose.material.icons.filled.FlightLand
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.Screen

import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    viewModel: SearchModelView,
    mainViewModel: MainViewModel
) {
    val state by viewModel.uiState.collectAsState()
    val availableDestinations = listOf("Hà Nội", "Đà Nẵng", "Hồ Chí Minh", "Nha Trang")
    val availableSeats = listOf("Business","Economy")
    var selectedSeat by remember { mutableStateOf(availableSeats[0]) }
    var selectedDeparture by remember { mutableStateOf(availableDestinations[0]) }
    var selectedArrive by remember { mutableStateOf(availableDestinations[1]) }
    var nameDeparture by remember { mutableStateOf("SGN") }
    var nameArrive by remember {mutableStateOf("HAN") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""
    LaunchedEffect(Unit) {
        viewModel.loadSearchBase()
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
                if (state.flights.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No flights found",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
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
                                    Icon(
                                        imageVector = Icons.Filled.FlightTakeoff,
                                        contentDescription = "Flight"
                                    )
                                    SelectDropdown(
                                        label = "Điểm đi",
                                        options = availableDestinations,
                                        selectedOption = selectedDeparture,
                                        onOptionSelected = { selectedDeparture = it }
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.FlightLand,
                                        contentDescription = "Flight"
                                    )
                                    SelectDropdown(
                                        label = "Điểm đến",
                                        options = availableDestinations,
                                        selectedOption = selectedArrive,
                                        onOptionSelected = { selectedArrive = it }
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    OutlinedTextField(
                                        value = selectedDate,
                                        onValueChange = { },
                                        label = { Text("Ngày khởi hành") },
                                        readOnly = true,
                                        trailingIcon = {
                                            IconButton(onClick = { showDatePicker = !showDatePicker }) {
                                                Icon(
                                                    imageVector = Icons.Default.DateRange,
                                                    contentDescription = "Select date"
                                                )
                                            }
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(64.dp)
                                    )

                                    if (showDatePicker) {
                                        DatePickerDialog(
                                            state = datePickerState,
                                            onDismissRequest = { showDatePicker = false },
                                            onDateSelected = {
                                                showDatePicker = false
                                                selectedDate = convertMillisToDate(it)
                                            }
                                        )
                                    }

                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Hạng ghế")
                                    Icon(
                                        imageVector = Icons.Filled.EventSeat,
                                        contentDescription = "Flight"
                                    )
                                    SelectDropdown(
                                        label = "Hạng ghế",
                                        options = availableSeats,
                                        selectedOption = selectedSeat,
                                        onOptionSelected = { selectedSeat = it }
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                ElevatedButton(
                                    onClick = {
                                        val flightRequest = FlightRequest(1L,2L,"07-01-2024","Business")
                                        navController.navigate("${Screen.SearchList.route}?departureAirport=${flightRequest.departureAirport}&arrivalAirport=${flightRequest.arrivalAirport}&departureTime=${flightRequest.departureTime}&seatClass=${flightRequest.seatClass}&nameDeparture=${nameDeparture}&nameArrive=${nameArrive}")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text("Tìm chuyến bay")
                                }
                            }
                    }
                }

                    LazyColumn(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                text = "Đề xuất cho bạn",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                        }
                        items(state.flights) { flight ->
                            FlightCard(flight)
                        }
                    }

                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialog(
    state: DatePickerState,
    onDismissRequest: () -> Unit,
    onDateSelected: (Long) -> Unit
) {
    val currentDateMillis = System.currentTimeMillis()

    Dialog(onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Column {
                DatePicker(
                    state = state,
                    modifier = Modifier.fillMaxWidth(),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        state.selectedDateMillis?.let { onDateSelected(it) }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Ok")
                }
            }
        }
    }
}


fun convertMillisToDate(millis: Long?): String {
    return millis?.let {
        val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        formatter.format(Date(it))
    } ?: ""
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDropdown(
    label: String,
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            value = selectedOption,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Dropdown"
                )
            },
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun FlightCard(flight: FlightResponse) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5FA)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text("07:45", fontWeight = FontWeight.Bold)
                    Text("${flight.codeDepartAirport}")
                }

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("1g 15p")
                    Text("Bay thẳng", textAlign = TextAlign.Center)
                }

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text("09:00")
                    Text("${flight.codeArriAirport}")
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text(
                        flight.airline,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${flight.busPrice} đ",
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )
                }
            }

        }
    }
}
