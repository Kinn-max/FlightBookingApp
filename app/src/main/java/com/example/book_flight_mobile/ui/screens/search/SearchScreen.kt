package com.example.book_flight_mobile.ui.screens.search

import android.annotation.SuppressLint
import android.app.Activity
import android.icu.util.Calendar
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel

import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightResponse
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
    val screenWidth = LocalConfiguration.current.screenWidthDp
    var offsetX by remember { mutableStateOf(0f) }
    val availableDestinations = listOf("Hà Nội", "Đà Nẵng", "Hồ Chí Minh", "Nha Trang")
    var selectedDestination by remember { mutableStateOf(availableDestinations[0]) }
    var selectedDate by remember { mutableStateOf("Chưa chọn ngày") }
    var showDatePickerDialog by remember { mutableStateOf(false) }


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
                    LazyRow(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.Gray)
                    ) {
                        items(10) { index ->
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .background(Color.White)
                                    .padding(4.dp)
                                    .zIndex(1f)
                                    .offset { IntOffset(offsetX.toInt(), 0) }
                                    .pointerInput(Unit) {
                                        detectHorizontalDragGestures { _, dragAmount ->
                                            offsetX += dragAmount
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                               Text("hi")
                            }
                        }
                    }


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
                                        selectedOption = selectedDestination,
                                        onOptionSelected = { selectedDestination = it }
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
                                        selectedOption = selectedDestination,
                                        onOptionSelected = { selectedDestination = it }
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Ngày đi")
                                    Text(
                                        text = selectedDate,
                                        modifier = Modifier
                                            .clickable {
                                                showDatePickerDialog = true
                                            }
                                    )
                                }

                                if (showDatePickerDialog) {
                                    val datePicker = MaterialDatePicker.Builder.datePicker()
                                        .setTitleText("Chọn ngày đi")
                                        .build()
                                    datePicker.addOnPositiveButtonClickListener { selection ->
                                        val date = LocalDate.ofEpochDay(selection / 86400000L)
                                        selectedDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                                        showDatePickerDialog = false
                                    }

                                    val context = LocalContext.current
                                    val activity = context as? Activity
                                    activity?.let {
                                        val fragmentTransaction = (it as FragmentActivity).supportFragmentManager.beginTransaction()
                                        datePicker.show(fragmentTransaction, "DATE_PICKER")
                                    }

                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Hạng ghế")
                                    Text("hi")
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                ElevatedButton(
                                    onClick = { /* Xử lý tìm chuyến bay */ },
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

