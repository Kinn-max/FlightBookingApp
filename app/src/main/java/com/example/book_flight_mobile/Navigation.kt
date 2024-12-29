package com.example.book_flight_mobile

import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Scaffold
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.book_flight_mobile.config.TokenManager
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.ui.screens.HomeScreen
import com.example.book_flight_mobile.ui.screens.HomeViewModel
import com.example.book_flight_mobile.ui.screens.loginandregister.AuthModelView
import com.example.book_flight_mobile.ui.screens.loginandregister.LoginScreen
import com.example.book_flight_mobile.ui.screens.loginandregister.RegisterScreen
import com.example.book_flight_mobile.ui.screens.profile.ProfileModelView
import com.example.book_flight_mobile.ui.screens.profile.ProfileScreen
import com.example.book_flight_mobile.ui.screens.ticket.HistoryTicketScreen
import com.example.book_flight_mobile.ui.screens.search.SearchModelView
import com.example.book_flight_mobile.ui.screens.search.SearchScreen
import com.example.book_flight_mobile.ui.screens.search.listsearch.SearchListScreen
import com.example.book_flight_mobile.ui.screens.ticket.detail.DetailTicketScreen
import com.example.book_flight_mobile.ui.screens.ticket.detail.SummaryTicketScreen


sealed class Screen(val route:String){
    object Home:Screen("home")
    object Login:Screen("login")
    object Register:Screen("register")
    object TicketHistoryDetail:Screen("ticket_history_detail")
    object Detail:Screen("detail")
    object SummaryTicket:Screen("summary")
    object Search:Screen("search")
    object HistoryTicket:Screen("booking_history")
    object Profile:Screen("profile")
    object SearchList:Screen("search_list")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val mainState = mainViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(mainState.value.error) {
        if (mainState.value.error != null && mainState.value.error != "") {
            Toast.makeText(context, mainState.value.error, Toast.LENGTH_LONG).show()
            mainViewModel.setError("")
        }
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.Home.route,
                    onClick = { navController.navigate(Screen.Home.route) },
                    label = { Text("Home") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home Icon")})
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.Search.route,
                    onClick = { navController.navigate(Screen.Search.route) },
                    label = { Text("Search") },
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Search Icon") }
                )
                NavigationBarItem(
                    selected = navController.currentDestination?.route == Screen.Profile.route,
                    onClick = {navController.navigate(Screen.Profile.route) },
                    label = { Text("Profile") },
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Profile Icon")  }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    navController = navController,
                    viewModel = homeViewModel,
                    mainViewModel = mainViewModel
                )
            }
            composable(Screen.Search.route) {
                val searchViewModel: SearchModelView = hiltViewModel()
                SearchScreen(
                    navController = navController,
                    viewModel = searchViewModel,
                    mainViewModel = mainViewModel
                )
            }
            composable(Screen.SearchList.route +"?departureAirport={departureAirport}&arrivalAirport={arrivalAirport}&departureTime={departureTime}&seatClass={seatClass}&nameDeparture={nameDeparture}&nameArrive={nameArrive}",
                arguments = listOf(
                    navArgument("departureAirport") { type = NavType.LongType },
                    navArgument("arrivalAirport") { type = NavType.LongType },
                    navArgument("departureTime") { type = NavType.StringType },
                    navArgument("seatClass") { type = NavType.StringType },
                    navArgument("nameDeparture") { type = NavType.StringType },
                    navArgument("nameArrive") { type = NavType.StringType },
                )
            ) { backStackEntry ->
            val departureAirport = backStackEntry.arguments?.getLong("departureAirport") ?: 0L
            val arrivalAirport = backStackEntry.arguments?.getLong("arrivalAirport") ?: 0L
            val departureTime = backStackEntry.arguments?.getString("departureTime") ?: ""
            val seatClass = backStackEntry.arguments?.getString("seatClass") ?: ""
            val departure = backStackEntry.arguments?.getString("nameDeparture") ?: ""
            val arrive = backStackEntry.arguments?.getString("nameArrive") ?: ""
            val flightRequest = FlightRequest(
                departureAirport = departureAirport,
                arrivalAirport = arrivalAirport,
                departureTime = departureTime,
                seatClass = seatClass
            )
            val searchViewModel: SearchModelView = hiltViewModel()
            SearchListScreen(
                navController = navController,
                viewModel = searchViewModel,
                mainViewModel = mainViewModel,
                flightRequest = flightRequest,
                departure=departure,
                arrive=arrive
            )
        }
            composable(Screen.Profile.route) {
                val profileModelView: ProfileModelView = hiltViewModel()
                val tokenManager: TokenManager = hiltViewModel()
                ProfileScreen(
                    navController = navController,
                    viewModel = profileModelView,
                    mainViewModel = mainViewModel,
                    tokenManager = tokenManager
                )
            }
            composable(Screen.HistoryTicket.route) {
                val profileModelView: ProfileModelView = hiltViewModel()
                HistoryTicketScreen(
                    navController = navController,
                    viewModel = profileModelView,
                    mainViewModel = mainViewModel
                )
            }
            composable(
                Screen.TicketHistoryDetail.route + "?id={id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val ticketId = backStackEntry.arguments?.getLong("id") ?: -1
                if (ticketId != -1L) {
                    DetailTicketScreen(
                        navController = navController,
                        viewModel = hiltViewModel(),
                        mainViewModel = mainViewModel,
                        id = ticketId
                    )
                }
            }
            composable(Screen.Login.route) {
                val loginModelView: AuthModelView = hiltViewModel()
                LoginScreen(
                    navController = navController,
                    viewModel = loginModelView,
                    mainViewModel = mainViewModel
                )
            }
            composable(Screen.Register.route) {
                val loginModelView: AuthModelView = hiltViewModel()
                RegisterScreen(
                    navController = navController,
                    viewModel = loginModelView,
                    mainViewModel = mainViewModel
                )
            }
            composable(
                Screen.SummaryTicket.route + "?id={id}",
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.LongType
                        defaultValue = -1
                    }
                )
            ) { backStackEntry ->
                val ticketId = backStackEntry.arguments?.getLong("id") ?: -1
                if (ticketId != -1L) {
                    SummaryTicketScreen(
                        navController = navController,
                        viewModel = hiltViewModel(),
                        mainViewModel = mainViewModel,
                        id = ticketId
                    )
                }
            }
        }
    }
}