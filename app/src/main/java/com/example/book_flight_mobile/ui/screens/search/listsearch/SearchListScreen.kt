package com.example.book_flight_mobile.ui.screens.search.listsearch

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowDown
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
import com.example.book_flight_mobile.ui.screens.BannerCard
import com.example.book_flight_mobile.ui.screens.FlightCard
import com.example.book_flight_mobile.ui.screens.search.SearchModelView
import com.example.book_flight_mobile.ui.screens.utils.EmptyFlight


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
    LaunchedEffect(Unit) {
        viewModel.searchRequest(flightRequest)
    }

    Scaffold(
        topBar = {
                CustomTopBarSearch("Chuyến đi từ ${departure} - ${arrive}", navController)
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
            ){
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
                    FlightCardShow(flight)
                }
            }
        }
    }
}

@Composable
fun FlightCardShow(flight: FlightResponse) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
//            .padding(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))

    ) {
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
//            Row(
//                modifier = Modifier
//                    .padding(start = 0.dp)
//                    .fillMaxWidth(),
//                horizontalArrangement = Arrangement.Center,
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                IconButton(onClick = {  }) {
//                    Icon(
//                        imageVector = Icons.Default.KeyboardArrowDown,
//                        contentDescription = "Back",
//                        tint = Color(0xFF1A94FF),
//                    )
//                }
//            }
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
