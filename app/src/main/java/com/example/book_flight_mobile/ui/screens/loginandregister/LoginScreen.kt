package com.example.book_flight_mobile.ui.screens.loginandregister

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.MainViewModel
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.config.TokenManager


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthModelView,
    mainViewModel: MainViewModel,
) {
    val state = viewModel.uiState.collectAsState()
    Scaffold (
        topBar = {
            CustomTopBarLogin("Đăng nhập tài khoản")
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (state.value.status) {
                is LoadStatus.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is LoadStatus.Error -> {
                    mainViewModel.setError(state.value.status.description)
                    viewModel.reset()
                }

                else -> {
                    Card(
                        modifier = Modifier.fillMaxWidth()
                            .padding(15.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5FA)),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedTextField(
                                    value = state.value.username,
                                    onValueChange = {
                                        viewModel.updateUsername(it)
                                    },
                                    label = { Text("Tên đăng nhập") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp)
                                )

                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                OutlinedTextField(
                                    value = state.value.password,
                                    onValueChange = {
                                        viewModel.updatePassword(it)
                                    },
                                    label = { Text("Mật khẩu") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(64.dp),
                                    visualTransformation = PasswordVisualTransformation()
                                )

                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp)
                                    .padding(4.dp)
                                    .clickable { viewModel.login(navController) }
                                    .background(Color(0xFF1A94FF), shape = RoundedCornerShape(4.dp))
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = "Login",
                                    color = Color.White,
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.fillMaxSize(),
                                    textAlign = TextAlign.Center
                                )
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Bạn chưa có tài khoản?",
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = "Đăng kí",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 16.sp,
                                        color = Color.Blue,
                                        textDecoration = TextDecoration.Underline
                                    ),
                                    modifier = Modifier.clickable {
                                        navController.navigate(Screen.Register.route)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

    }
}

@Composable
fun CustomTopBarLogin(title: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color(0xFF1A94FF))
            .padding(start = 16.dp, end = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
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
