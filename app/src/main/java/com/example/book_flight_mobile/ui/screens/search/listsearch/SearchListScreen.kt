package com.example.book_flight_mobile.ui.screens.search.listsearch

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.R
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.ui.screens.search.SearchModelView
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.rememberModalBottomSheetState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Date


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
    var flightResponse by remember { mutableStateOf<FlightResponse?>(null) }
    val scope = rememberCoroutineScope()
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
                    ecoPrice = 0.0,
                    busPrice = 0.0
                )
            )
        }
        ,
        sheetBackgroundColor = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                CustomTopBarSearch("Chuyến đi từ $departure - $arrive", navController)
            }
        ) { padding ->
            Column(modifier = Modifier.padding(padding)) {
                LazyRow(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                        .background(Color(0xFFFFFF)),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(6) {
                        ListDay()
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
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

                LazyColumn(
                    modifier = Modifier
                        .padding(16.dp)
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
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlightDetailContent(scope: CoroutineScope, sheetState: ModalBottomSheetState, flightResponse: FlightResponse) {
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
                                Text(
                                    text = "${flightResponse.ecoPrice}",
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
                                    text = "${flightResponse.ecoPrice}",
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
                                        color = Color(0xFF27272A),
                                        fontWeight = FontWeight.W500,
                                        fontSize = 16.sp,
                                        lineHeight = 18.sp
                                    )
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
                                        color = Color(0xFF27272A),
                                        fontWeight = FontWeight.W500,
                                        fontSize = 16.sp,
                                        lineHeight = 18.sp
                                    )
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = "0 đ",
                                    style = TextStyle(
                                        color = Color(0xFF27272A),
                                        fontWeight = FontWeight.W500,
                                        fontSize = 16.sp,
                                        lineHeight = 18.sp
                                    )
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
                                        color = Color(0xFF27272A),
                                        fontWeight = FontWeight.W500,
                                        fontSize = 16.sp,
                                        lineHeight = 18.sp
                                    )
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
        Button(
            onClick = {
                // Handle button click action here
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
                .padding(8.dp)
                .background(Color(0xFF1A94FF), shape = RoundedCornerShape(4.dp)),
            contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 8.dp, bottom = 8.dp)
        ) {
            Text(
                text = "Chọn",
                color = Color.White,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun FlightCardShow(flight: FlightResponse, onFlightSelected: (FlightResponse) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        onClick = {
            onFlightSelected(flight)
        }
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
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,) {
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

                Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.End) {
                    Text(
                        text = "${flight.busPrice} đ",
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
            Row(
                modifier = Modifier
                    .padding(start = 0.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = {  }) {
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
                                flight.airline,
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
                                flight.codeDepartAirport,
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
                                flight.codeArriAirport,
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
@Composable
fun ListDay() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .fillMaxSize()
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
                .padding(8.dp)
                .clip(RoundedCornerShape(6.dp)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Thứ 2",
                color = Color(0xFF27272A),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    lineHeight = 27.sp
                )
            )
            Text(
                text = "01/02/2022",
                color = Color(0xFF808089),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    textAlign = TextAlign.Center
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
