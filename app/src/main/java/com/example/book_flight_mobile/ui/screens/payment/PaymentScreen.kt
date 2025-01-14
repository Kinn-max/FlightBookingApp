package com.example.book_flight_mobile.ui.screens.payment


import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.R
import com.example.book_flight_mobile.Screen
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PaymentScreen(
    navController: NavHostController,
    url: String
) {

    val context = LocalContext.current
    Button (onClick = {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, Uri.parse(url))
    }) {
        Text("Thanh toán")
    }

//    if (url.isNotEmpty()) {
//        var isLoading by remember { mutableStateOf(true) }
//        var isSuccess by remember { mutableStateOf(false) }
//        Log.e("Url", "Url: ${url}")
//        Box(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//            if (!isSuccess) {
//                AndroidView(
//                    factory = { context ->
//                        WebView(context).apply {
//                            settings.javaScriptEnabled = true
//                            settings.domStorageEnabled = true
//
//                            webViewClient = object : WebViewClient() {
//                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                                    super.onPageStarted(view, url, favicon)
//                                    isLoading = true
//                                }
//
//                                override fun onPageFinished(view: WebView?, url: String?) {
//                                    super.onPageFinished(view, url)
//                                    isLoading = false
//                                }
//
//                                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                                    val newUrl = request?.url.toString()
//                                    when {
//                                        newUrl.contains("success") -> {
//                                            isSuccess = true
//                                        }
//                                        newUrl.contains("failure") -> {
////                                            navController.navigate("failureScreen")
//                                        }
//                                    }
//                                    return false
//                                }
//                            }
//
//                            loadUrl(url)
//                        }
//                    },
//                    modifier = Modifier.fillMaxSize()
//                )
//            } else {
//
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.success),
//                        contentDescription = "Empty flight",
//                        modifier = Modifier
//                            .size(160.dp)
//                            .padding(bottom = 16.dp)
//                    )
//                    Text(
//                        text = "Thanh toán thành công",
//                        color = Color(0xFF27272A),
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 18.sp,
//                            lineHeight = 24.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentWidth(Alignment.CenterHorizontally)
//                    )
//                    Text(
//                        text = "Tham khảo hàng trăm chuyến bay khác trên Traveloka ngay bây giờ!",
//                        color = Color(0xFF27272A),
//                        style = TextStyle(
//                            fontWeight = FontWeight.Normal,
//                            fontSize = 16.sp,
//                            lineHeight = 21.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentWidth(Alignment.CenterHorizontally)
//                    )
//                    Row(
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        modifier = Modifier.fillMaxWidth(),
//                    ) {
//                        Column(modifier = Modifier.fillMaxWidth()) {
//                            Row(
//                                modifier = Modifier.fillMaxWidth(),
//                                verticalAlignment = Alignment.CenterVertically,
//                            ) {
//                                Column(
//                                    modifier = Modifier.weight(1f),
//                                    horizontalAlignment = Alignment.Start
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .height(50.dp)
//                                            .padding(4.dp)
//                                            .clickable {
////                                               //
//                                            }
//                                            .background(androidx.compose.ui.graphics.Color.White, shape = RoundedCornerShape(4.dp))
//                                            .padding(12.dp)
//                                    ) {
//                                        Text(
//                                            text = "Trang chủ",
//                                            color = Color(0xFF1A94FF),
//                                            style = TextStyle(
//                                                fontSize = 16.sp,
//                                                fontWeight = FontWeight.Bold
//                                            ),
//                                            modifier = Modifier.fillMaxSize(),
//                                            textAlign = TextAlign.Center
//                                        )
//                                    }
//                                }
//                                Column(
//                                    modifier = Modifier.weight(1f),
//                                    horizontalAlignment = Alignment.End
//                                ) {
//                                    Box(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .height(50.dp)
//                                            .padding(4.dp)
//                                            .clickable {
//                                             //
//                                            }
//                                            .background(Color(0xFF1A94FF), shape = RoundedCornerShape(4.dp))
//                                            .padding(12.dp)
//                                    ) {
//                                        Text(
//                                            text = "Chi tiết vé",
//                                            color = androidx.compose.ui.graphics.Color.White,
//                                            style = TextStyle(
//                                                fontSize = 16.sp,
//                                                fontWeight = FontWeight.Bold
//                                            ),
//                                            modifier = Modifier.fillMaxSize(),
//                                            textAlign = TextAlign.Center
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//
//            if (isLoading) {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//        }
//    }
}

