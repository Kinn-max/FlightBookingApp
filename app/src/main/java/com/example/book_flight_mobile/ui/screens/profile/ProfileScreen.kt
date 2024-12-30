package com.example.book_flight_mobile.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.R
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.config.TokenManager
@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileModelView,
    mainViewModel: MainViewModel,
    tokenManager: TokenManager
) {

    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.personalInformation()
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(Color(0xFFFFFFFF)),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ){
                        Text(
                            text = "Hồ sơ của bạn",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 21.sp,
                            color = Color(0xFF27272A),
                        )

                    }
                    Box{
                        Text("${tokenManager.getUserName()}")
                    }
//                    val token = tokenManager.getToken()
//                    if (token == null ){
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Start
//                        ){
//
//                        }
//                    }else{
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.End,
//                        ){
//                            Box(
//                                modifier = Modifier
//                                    .padding(end = 8.dp)
//                            ){
//                                Text("${tokenManager.getUserName()}",
//                                    style = TextStyle(
//                                        fontWeight = FontWeight.W500,
//                                        fontSize = 16.sp,
//                                        lineHeight = 21.sp,
//                                    ),)
//                            }
//                            Image(
//                                painter = painterResource(id = R.drawable.user),
//                                contentDescription = "User avatar",
//                                contentScale = ContentScale.Crop,
//                                modifier = Modifier
//                                    .size(28.dp)
//                                    .clip(CircleShape)
//                                    .border(2.dp, Color.Gray, CircleShape)
//                            )
//                        }
//                    }
                }
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (state.status) {
                is LoadStatus.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is LoadStatus.Error -> {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.status.description,
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center
                        )
                    }
                    mainViewModel.setError(state.status.description)
                    viewModel.reset()
                }

                else -> {
                    if(state.info == null){
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                                Text(
                                    "Bạn phải đăng nhập trước !!!",
                                )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                ElevatedButton(onClick = {
                                    navController.navigate(Screen.Login.route)
                                }, modifier = Modifier.padding(16.dp)) {
                                    Text("Đăng nhập")
                                }
                            }
                        }
                    }else{
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Spacer(modifier = Modifier.height(12.dp))
                                Column( modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp),) {
                                    Card(
                                        modifier = Modifier.fillMaxWidth(),
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color(
                                                0xFFF5F5FA
                                            )
                                        ),
                                        elevation = CardDefaults.cardElevation(4.dp)
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
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        "Full name: ",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W700,
                                                            fontSize = 16.sp,
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
                                                        text = "Trần Chung Kiên",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W400,
                                                            fontSize = 16.sp,
                                                            lineHeight = 21.sp,
                                                            color = Color(0xFF27272A)
                                                        ),
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        "Date of birth: ",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W700,
                                                            fontSize = 16.sp,
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
                                                        text = "07/01/2004",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W400,
                                                            fontSize = 16.sp,
                                                            lineHeight = 21.sp,
                                                            color = Color(0xFF27272A)
                                                        ),
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        "Email: ",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W700,
                                                            fontSize = 16.sp,
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
                                                        text = "kiendeptrai@gmail.com",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W400,
                                                            fontSize = 16.sp,
                                                            lineHeight = 21.sp,
                                                            color = Color(0xFF27272A)
                                                        ),
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        "Phone Number: ",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W700,
                                                            fontSize = 16.sp,
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
                                                        text = "123456789",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W400,
                                                            fontSize = 16.sp,
                                                            lineHeight = 21.sp,
                                                            color = Color(0xFF27272A)
                                                        ),
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(8.dp))
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.Start
                                                ) {
                                                    Text(
                                                        "Address: ",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W700,
                                                            fontSize = 16.sp,
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
                                                        text = "Đức Thọ, Hà Tĩnh",
                                                        style = TextStyle(
                                                            fontWeight = FontWeight.W400,
                                                            fontSize = 16.sp,
                                                            lineHeight = 21.sp,
                                                            color = Color(0xFF27272A)
                                                        ),
                                                    )
                                                }
                                            }
                                            Spacer(modifier = Modifier.height(14.dp))
                                            Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Column(
                                                        modifier = Modifier.weight(1f),
                                                        horizontalAlignment = Alignment.Start
                                                    ) {
                                                        ElevatedButton(
                                                            onClick = { /*  */ },
                                                            modifier = Modifier.padding( 16.dp),
                                                            colors = ButtonDefaults.elevatedButtonColors(
                                                                containerColor = Color(0xFF397CBF),
                                                                contentColor = Color(0xFFFDFEFE)
                                                            )
                                                        ) {
                                                            Text("Cập nhật "
                                                                ,style = TextStyle(
                                                                    fontWeight = FontWeight.W700,
                                                                fontSize = 16.sp,
                                                                lineHeight = 21.sp,
                                                            ))
                                                        }
                                                    }
                                                Column(
                                                    modifier = Modifier.weight(1f),
                                                    horizontalAlignment = Alignment.End
                                                ) {
                                                    ElevatedButton(
                                                        onClick = {  navController.navigate(Screen.HistoryTicket.route)},
                                                        modifier = Modifier.padding(16.dp),
                                                        colors = ButtonDefaults.elevatedButtonColors(
                                                            containerColor = Color(0xFF5d6d7e),
                                                            contentColor = Color(0xFFFDFEFE)
                                                        )
                                                    ) {
                                                        Text("Lịch sử ",
                                                            style = TextStyle(
                                                                fontWeight = FontWeight.W700,
                                                            fontSize = 16.sp,
                                                            lineHeight = 21.sp,
                                                        ))
                                                    }
                                                }

                                            }
                                        }
                                    }
                                }
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f),
                                            horizontalAlignment = Alignment.CenterHorizontally
                                        ) {
                                            ElevatedButton(onClick = { }, modifier = Modifier.padding(16.dp)) {
                                                Text("Đăng xuất")
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
