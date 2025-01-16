package com.example.book_flight_mobile.ui.screens.payment


import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
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
import androidx.compose.material3.ButtonDefaults
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
    url: String,
    onPaymentSuccess: () -> Unit,
    onPaymentFailure: () -> Unit
) {

    val context = LocalContext.current

//    Button (onClick = {
//        val customTabsIntent = CustomTabsIntent.Builder().build()
//        customTabsIntent.launchUrl(context, Uri.parse(url))
//    }) {
//        Text("Thanh toán")
//    }
//
//    if (url.isNotEmpty()) {
//        var isLoading by remember { mutableStateOf(true) }
//        var isSuccess by remember { mutableStateOf(false) }
//
//        Log.e("Url", "Url: $url")
//
//        Box(modifier = Modifier.fillMaxSize()) {
//            if (!isSuccess) {
//                AndroidView(
//                    factory = { context ->
//                        WebView(context).apply {
//                            settings.apply {
//                                javaScriptEnabled = true
//                                domStorageEnabled = true
//                                allowFileAccess = true
//                                allowContentAccess = true
//                                cacheMode = WebSettings.LOAD_DEFAULT
//                                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE // Cho phép nội dung hỗn hợp
//                            }
//
//                            webViewClient = object : WebViewClient() {
//                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//                                    super.onPageStarted(view, url, favicon)
//                                    isLoading = true
//                                    Log.d("WebView", "Page started loading: $url")
//                                }
//
//                                override fun onPageFinished(view: WebView?, url: String?) {
//                                    super.onPageFinished(view, url)
//                                    isLoading = false
//                                    Log.d("WebView", "Page finished loading: $url")
//                                }
//
//                                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                                    val newUrl = request?.url.toString()
//                                    Log.d("WebView", "Navigating to URL: $newUrl")
//                                    when {
//                                        newUrl.contains("success", ignoreCase = true) -> {
//                                            isSuccess = true
//                                            Log.d("WebView", "Payment success detected.")
//                                            return true
//                                        }
//                                        newUrl.contains("failure", ignoreCase = true) -> {
//                                            navController.navigate("failureScreen")
//                                            Log.d("WebView", "Payment failure detected.")
//                                            return true
//                                        }
//                                        else -> {
//                                            view?.loadUrl(newUrl) // Tiếp tục tải URL
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
//                // Hiển thị giao diện thành công
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    Image(
//                        painter = painterResource(id = R.drawable.success),
//                        contentDescription = "Thanh toán thành công",
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
//                        textAlign = TextAlign.Center
//                    )
//                    Text(
//                        text = "Tham khảo hàng trăm chuyến bay khác trên ứng dụng ngay bây giờ!",
//                        color = Color(0xFF27272A),
//                        style = TextStyle(
//                            fontWeight = FontWeight.Normal,
//                            fontSize = 16.sp,
//                            lineHeight = 21.sp
//                        ),
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(top = 24.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Button(
//                            onClick = { navController.navigate("homeScreen") },
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(end = 8.dp)
//                        ) {
//                            Text(
//                                text = "Trang chủ",
//                                color = Color(0xFF1A94FF),
//                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                            )
//                        }
//                        Button(
//                            onClick = { navController.navigate("ticketDetailScreen") },
//                            modifier = Modifier
//                                .weight(1f)
//                                .padding(start = 8.dp)
//                        ) {
//                            Text(
//                                text = "Chi tiết vé",
//                                color = Color.White,
//                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
//                            )
//                        }
//                    }
//                }
//            }
//
//            // Hiển thị vòng tròn loading khi trang đang tải
//            if (isLoading) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(Color(0x80000000)), // Màu nền mờ khi loading
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator()
//                }
//            }
//        }
//    }

    if (url.isNotEmpty()) {
        var isLoading by remember { mutableStateOf(true) }
        var isSuccess by remember { mutableStateOf(false) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Log.e("Url", "Url: $url")

        Box(modifier = Modifier.fillMaxSize()) {
            if (!isSuccess) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            settings.apply {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                allowFileAccess = true
                                allowContentAccess = true
                                cacheMode = WebSettings.LOAD_DEFAULT
                                mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
                            }

                            webViewClient = object : WebViewClient() {
                                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                    super.onPageStarted(view, url, favicon)
                                    isLoading = true
                                    errorMessage = null // Reset error message when page starts loading
                                    Log.d("WebView", "Page started loading: $url")
                                }

                                override fun onPageFinished(view: WebView?, url: String?) {
                                    super.onPageFinished(view, url)
                                    isLoading = false
                                    Log.d("WebView", "Page finished loading: $url")
                                }

                                override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
                                    super.onReceivedError(view, errorCode, description, failingUrl)
                                    isLoading = false
                                    errorMessage = "Lỗi tải trang: $description"
                                    Log.e("WebView", "Error: $description")
                                }

                                override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                    val newUrl = request?.url.toString()
                                    Log.d("WebView", "Navigating to URL: $newUrl")
                                    when {
                                        newUrl.contains("success", ignoreCase = true) -> {
                                            isSuccess = true
                                            Log.d("WebView", "Payment success detected.")
                                            return true
                                        }
                                        newUrl.contains("failure", ignoreCase = true) -> {
                                            navController.navigate("failureScreen")
                                            Log.d("WebView", "Payment failure detected.")
                                            return true
                                        }
                                        else -> {
                                            view?.loadUrl(newUrl)
                                        }
                                    }
                                    return false
                                }
                            }

                            loadUrl(url)
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Hiển thị giao diện thành công
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.success),
                        contentDescription = "Thanh toán thành công",
                        modifier = Modifier
                            .size(160.dp)
                            .padding(bottom = 16.dp)
                    )
                    Text(
                        text = "Thanh toán thành công",
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            lineHeight = 24.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Tham khảo hàng trăm chuyến bay khác trên ứng dụng ngay bây giờ!",
                        color = Color(0xFF27272A),
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            lineHeight = 21.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { navController.navigate("homeScreen") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 8.dp)
                        ) {
                            Text(
                                text = "Trang chủ",
                                color = Color(0xFF1A94FF),
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            )
                        }
                        Button(
                            onClick = { navController.navigate("ticketDetailScreen") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 8.dp)
                        ) {
                            Text(
                                text = "Chi tiết vé",
                                color = Color.White,
                                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }

            // Hiển thị vòng tròn loading khi trang đang tải
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x80000000)), // Màu nền mờ khi loading
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Color(0xFF1A94FF))
                }
            }

            // Hiển thị thông báo lỗi nếu có
            errorMessage?.let {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0x80000000)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }

}

