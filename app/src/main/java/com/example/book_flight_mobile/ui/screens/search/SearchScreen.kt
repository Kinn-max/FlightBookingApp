package com.example.book_flight_mobile.ui.screens.search

import android.annotation.SuppressLint
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.R
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightRequest
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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        ) {
            CustomTopBarSearchMain()
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .absoluteOffset(y = (120.dp / 2))
        ) {
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
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.FlightTakeoff,
                                            contentDescription = "Flight"
                                        )
                                        SelectDropdownCustom(
                                            label = "Điểm đi",
                                            options = availableDestinations,
                                            selectedOption = selectedDeparture,
                                            onOptionSelected = { selectedDeparture = it }
                                        )
                                    }

                                    Row(
                                        modifier = Modifier
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
                                            ).padding(top = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.FlightLand,
                                            contentDescription = "Flight"
                                        )
                                        SelectDropdownCustom(
                                            label = "Điểm đến",
                                            options = availableDestinations,
                                            selectedOption = selectedArrive,
                                            onOptionSelected = { selectedArrive = it }
                                        )
                                    }

                                    Box(
                                        modifier = Modifier
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
                                            ).padding(top = 6.dp),
                                    ) {
                                        OutlinedTextField(
                                            value = selectedDate,
                                            onValueChange = { },
                                            label = { Text("Ngày khởi hành") },
                                            readOnly = true,
                                            trailingIcon = {
                                                IconButton(onClick = {
                                                    showDatePicker = !showDatePicker
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Default.DateRange,
                                                        contentDescription = "Select date"
                                                    )
                                                }
                                            },
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(64.dp),
                                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                                focusedBorderColor = Color.Transparent,
                                                unfocusedBorderColor = Color.Transparent,
                                                focusedLabelColor = MaterialTheme.colorScheme.primary,
                                                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
                                            )
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
                                    Row(
                                        modifier = Modifier
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
                                            ).padding(top = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.EventSeat,
                                            contentDescription = "Flight"
                                        )
                                        SelectDropdownCustom(
                                            label = "Hạng ghế",
                                            options = availableSeats,
                                            selectedOption = selectedSeat,
                                            onOptionSelected = { selectedSeat = it }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(16.dp))

                                    ElevatedButton(
                                        onClick = {
                                            val flightRequest =
                                                FlightRequest(1L, 2L, "07-01-2024", "Business")
                                            navController.navigate("${Screen.SearchList.route}?departureAirport=${flightRequest.departureAirport}&arrivalAirport=${flightRequest.arrivalAirport}&departureTime=${flightRequest.departureTime}&seatClass=${flightRequest.seatClass}&nameDeparture=${nameDeparture}&nameArrive=${nameArrive}")
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text("Tìm chuyến bay")
                                    }
                                }
                            }
                        }
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                "Tra cứu gần đây",
                                style = TextStyle(
                                    color = Color(0xFF27272A),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 24.sp,
                                    textAlign = TextAlign.Start,
                                ))
                        }
                        LazyRow(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(6) {
                                HistorySearch()
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
fun SelectDropdownCustom(
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
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onSurface
            ),
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
fun CustomTopBarSearchMain() {
    Image(
        painter = painterResource(id = R.drawable.search),
        contentDescription = "Airline logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )
}



@Composable
fun HistorySearch() {
    Card(
        modifier = Modifier
            .width(273.dp)
            .padding(4.dp)
            .background(Color(0xFFFFFF)),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "HAN-SGN",
                        style = TextStyle(
                            color = Color(0xFF1A94FF),
                            fontSize = 16.sp,
                            fontWeight = FontWeight(500),
                            lineHeight = 21.sp,
                            textAlign = TextAlign.Start,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

            }
            Row(modifier = Modifier.fillMaxWidth().padding(top =  8.dp),horizontalArrangement = Arrangement.SpaceBetween) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    Text(
                        "Ngày: 03/11/2021",
                        style = TextStyle(
                            color = Color(0xFF808089),
                            fontSize = 14.sp,
                            fontWeight = FontWeight(400),
                            lineHeight = 18.sp,
                            textAlign = TextAlign.Start,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

            }

        }
    }
}
