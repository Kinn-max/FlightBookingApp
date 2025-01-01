package com.example.book_flight_mobile.ui.screens.payment


import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.Screen
import com.google.android.material.progressindicator.CircularProgressIndicator

@Composable
fun PaymentScreen(
    navController: NavHostController,
    url: String
) {

    val context = LocalContext.current
    Button(onClick = {
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
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Column (
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = "Thanh toán thành công!",
//                            style = MaterialTheme.typography.headlineLarge,
//                            modifier = Modifier.padding(bottom = 16.dp)
//                        )
//                        Button (onClick = {
//                            navController.navigate(Screen.Home.route)
//                        }) {
//                            Text(text = "Quay về trang chủ")
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

