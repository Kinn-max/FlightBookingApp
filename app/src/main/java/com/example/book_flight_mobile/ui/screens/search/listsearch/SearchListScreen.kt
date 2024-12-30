package com.example.book_flight_mobile.ui.screens.search.listsearch

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.api.RetrofitInstance
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.models.PlaneResponse
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.ui.screens.search.SearchModelView
import com.example.book_flight_mobile.ui.screens.utils.CardLoading
import com.example.book_flight_mobile.ui.screens.utils.EmptyFlight
import com.example.book_flight_mobile.ui.screens.utils.base64ToBitmap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SearchListScreen(
    navController: NavHostController,
    viewModel: SearchModelView,
    mainViewModel: MainViewModel,
    flightRequest: FlightRequest,
    departure: String,
    arrive: String
) {

    val uiState by viewModel.uiState.collectAsState()
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val currentDate = LocalDate.parse(flightRequest.departureTime, dateFormatter)
    val daysList = (0 until 7).map { currentDate.plusDays(it.toLong()) }
    var flightResponse by remember { mutableStateOf<FlightResponse?>(null) }
    val scope = rememberCoroutineScope()
    var selectedDate by remember { mutableStateOf(daysList.first()) }
    LaunchedEffect(Unit) {
        viewModel.searchRequest(flightRequest)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetContent = {
            FlightDetailContent(
                scope = scope,
                sheetState = sheetState,
                flightResponse = flightResponse ?: FlightResponse(
                    id = 0L,
                    codeFlight = "",
                    departureTime = Date(0),
                    arrivalTime = Date(0),
                    departureAirport = "",
                    departureLocation = "",
                    arrivalLocation = "",
                    arrivalAirport = "",
                    codeDepartAirport = "",
                    codeArriAirport = "",
                    airline = "",
                    logoAirline = "",
                    plane = PlaneResponse(id = 0L,0,0,"",""),
                    ecoPrice = 0.0,
                    busPrice = 0.0,
                    status = true,
                    ticketId = 0L,
                    seats = emptyList()
                ),
                navController = navController
            )
        },
        sheetBackgroundColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                CustomTopBarSearch("Chuyến đi từ $departure - $arrive", navController)
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    LazyRow(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(daysList) { day ->
                            ListDay(
                                date = day,
                                dateFormatter = dateFormatter,
                                isSelected = selectedDate == day,
                                onClick = {
                                    selectedDate = it
                                    val updatedFlightRequest = flightRequest.copy(
                                        departureTime = it.format(dateFormatter)
                                    )
                                    viewModel.searchRequest(updatedFlightRequest)
                                }
                            )
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Giá hiển thị đã bao gồm thuế và phí",
                        color = Color(0xFF808089),
                        style = TextStyle(
                            fontWeight = FontWeight(400),
                            fontSize = 16.sp,
                            lineHeight = 18.sp
                        )
                    )
                }
                when (uiState.status) {
                    is LoadStatus.Loading -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(4) {
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
                        if (uiState.flightSearch.isEmpty()) {
                            EmptyFlight()
                        } else {
                            LazyColumn(
                                modifier = Modifier
                                    .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(uiState.flightSearch) { flight ->
                                    FlightCardShow(flight, onFlightSelected = { selectedFlight ->
                                        flightResponse = selectedFlight
                                        scope.launch { sheetState.show() }
                                    })
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
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlightDetailContent(scope: CoroutineScope, sheetState: ModalBottomSheetState, flightResponse: FlightResponse,navController: NavHostController) {
    Column(
        modifier = Modifier
            .background(Color(0xFFF5F5FA))
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
                text = "Chi tiết chuyến bay",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            IconButton(
                onClick = {
                    scope.launch {
                        sheetState.hide()
                    }
                }
            ) {
                Icon(Icons.Default.Close, contentDescription = "Close")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            FlightCardDetail(flightResponse)
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
                                val formattedEcoPrice =
                                    NumberFormat.getNumberInstance(Locale("vi", "VN")).format(flightResponse.ecoPrice)
                                Text(
                                    text = "${formattedEcoPrice} đ",
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
                                val formattedBusPrice =
                                    NumberFormat.getNumberInstance(Locale("vi", "VN")).format(flightResponse.busPrice)
                                Text(
                                    text = "${formattedBusPrice} đ",
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(4.dp)
                .clickable {
                    navController.navigate("${Screen.SummaryTicket.route}?id=${flightResponse.id}")
                }
                .background(Color(0xFF1A94FF), shape = RoundedCornerShape(4.dp))
                .padding(12.dp)
        ) {
            Text(
                text = "Chọn",
                color = Color.White,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxSize(),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun FlightCardShow(flight: FlightResponse, onFlightSelected: (FlightResponse) -> Unit) {
    Card(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = {
            onFlightSelected(flight)
        }
    )  {
        Column(modifier = Modifier
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val formattedTime = dateFormat.format(flight.departureTime)
                    Text(
                        text = formattedTime,
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.W700,
                            fontSize = 18.sp,
                            lineHeight = 21.sp
                        )
                    )
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
                    val durationMillis = flight.arrivalTime.time - flight.departureTime.time
                    val hours = (durationMillis / (1000 * 60 * 60)).toInt()
                    val minutes = (durationMillis / (1000 * 60) % 60).toInt()
                    Text(
                        text = "${hours}g ${minutes}p",
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
                    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                    val formattedTime = dateFormat.format(flight.arrivalTime)
                    Text(
                        formattedTime,
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
                    .height(60.dp),
            ) {
                Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                    Row(
                        modifier = Modifier
                        .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        val bitmap = base64ToBitmap(flight.logoAirline)
                        val imageBitmap = bitmap?.asImageBitmap()
                        if (imageBitmap != null) {
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = "Airline logo",
                                modifier = Modifier
                                    .size(height = 60.dp, width = 100.dp)

                            )
                        } else {
                            Text(
                                text = "No Image",
                                color = Color.Gray,
                                style = TextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp
                                )
                            )
                        }
//                        Text(
//                            flight.airline,
//                            style = TextStyle(
//                                color = Color(0xFF27272A),
//                                fontWeight = FontWeight.W500,
//                                fontSize = 14.sp,
//                                lineHeight = 18.sp
//                            ),
//                            modifier = Modifier.align(Alignment.CenterVertically)
//                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center
                ) {
                    val formattedEcoPrice =
                        NumberFormat.getNumberInstance(Locale("vi", "VN")).format(flight.ecoPrice)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
                            Text(
                                text = "$formattedEcoPrice đ",
                                style = TextStyle(
                                    color = Color(0xFF1A94FF),
                                    fontFamily = FontFamily.Default,
                                    fontWeight = FontWeight.W700,
                                    fontSize = 18.sp,
                                    lineHeight = 21.sp
                                ),
                                modifier = Modifier.align(Alignment.CenterVertically)
                            )
                    }

                }
            }
            Row(
                modifier = Modifier
                    .padding(start = 0.dp)
                    .height(30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {
                    onFlightSelected(flight)
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = "Back",
                        tint = Color(0xFF1A94FF),
                    )
                }
            }
        }
    }
}

@Composable
fun FlightCardDetail(flight: FlightResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ){
        Column(modifier = Modifier
            .background(Color.White)
            .padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(modifier = Modifier.weight(2f), horizontalAlignment = Alignment.Start) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        val bitmap = base64ToBitmap(flight.logoAirline)
                        val imageBitmap = bitmap?.asImageBitmap()
                        if (imageBitmap != null) {
                            Image(
                                bitmap = imageBitmap,
                                contentDescription = "Airline logo",
                                modifier = Modifier
                                    .size(height = 70.dp, width = 100.dp)

                            )
                        } else {
                            Text(
                                text = "No Image",
                                color = Color.Gray,
                                style = TextStyle(
                                    fontWeight = FontWeight.W400,
                                    fontSize = 14.sp
                                )
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 20.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                flight.airline,
                                style = TextStyle(
                                    color = Color(0xFF27272A),
                                    fontWeight = FontWeight.W500,
                                    fontSize = 16.sp,
                                    lineHeight = 18.sp
                                )
                            )
                            Text(
                                text = flight.plane.name,
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
                            )
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.Start) {
                            Text(
                                flight.codeDepartAirport,
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W700,
                                    fontSize = 18.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val formattedTime = dateFormat.format(flight.departureTime)
                            Text(
                                formattedTime,
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            val dayFormat = SimpleDateFormat("EE, dd-MM-yyyy", Locale("vi", "VN"))
                            val formattedDay = dayFormat.format(flight.departureTime)
                            Text(
                                formattedDay,
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
                            val durationMillis = flight.arrivalTime.time - flight.departureTime.time
                            val hours = (durationMillis / (1000 * 60 * 60)).toInt()
                            val minutes = (durationMillis / (1000 * 60) % 60).toInt()
                            Text(
                                text = "${hours}g ${minutes}p",
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
                                flight.codeArriAirport,
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W700,
                                    fontSize = 18.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                            val formattedTime = dateFormat.format(flight.arrivalTime)
                            Text(
                                formattedTime,
                                color = Color(0xFF27272A),
                                style = TextStyle(
                                    fontWeight = FontWeight.W500,
                                    fontSize = 14.sp,
                                    lineHeight = 21.sp
                                )
                            )
                            val dayFormat = SimpleDateFormat("EE, dd-MM-yyyy", Locale("vi", "VN"))
                            val formattedDay = dayFormat.format(flight.arrivalTime)
                            Text(
                                formattedDay,
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

val dayOfWeekInVietnamese = listOf(
    "Chủ nhật", "Thứ hai", "Thứ ba", "Thứ tư", "Thứ năm", "Thứ sáu", "Thứ bảy"
)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListDay(date: LocalDate, dateFormatter: DateTimeFormatter, isSelected: Boolean,
            onClick: (LocalDate) -> Unit ) {
    val dayOfWeek = date.dayOfWeek.value
    val vietnameseDay = dayOfWeekInVietnamese[dayOfWeek % 7]
    val cardBackgroundColor = if (isSelected) Color(0xFF1A94FF) else Color.White
    val textColorBottom = if (isSelected) Color.White else Color(0xFF808089)
    val textColorTop = if (isSelected) Color.White else Color(0xFF27272A)
    Card(
        modifier = Modifier
            .height(55.dp)
            .clickable { onClick(date) }
            .then(
                Modifier.drawWithContent {
                    drawContent()
                    drawLine(
                        color = Color(0xFFEBEBF0),
                        strokeWidth = 1.dp.toPx(),
                        start = Offset(size.width, 0f),
                        end = Offset(size.width, size.height)
                    )
                }
            )
            .padding(end = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(cardBackgroundColor)
                .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(6.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = vietnameseDay,
                color = textColorTop,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    lineHeight = 27.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = date.format(dateFormatter),
                color = textColorBottom,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            )
        }
    }
}


@Composable
fun CustomTopBarSearch(title: String, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF1A94FF))
            .padding(start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(onClick = { navController.popBackStack() }) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color.White, shape = CircleShape)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color(0xFF1A94FF)
                )
            }
        }
        Text(
            text = title,
            color = Color.White,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                lineHeight = 27.sp
            ),
            fontWeight = FontWeight.Bold
        )
    }
}
