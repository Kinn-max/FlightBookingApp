package com.example.book_flight_mobile.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.AirportResponse
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.repositories.AirportRepository
import com.example.book_flight_mobile.repositories.FlightRepository
import com.example.book_flight_mobile.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val flight:List<FlightResponse> = emptyList(),
    val status:LoadStatus = LoadStatus.Innit(),
)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val log: MainLog?,
    private val flightRepository: FlightRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadHomeApp()
    }

    private fun loadHomeApp(){
    viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val flights = flightRepository.loadFlightHome()
                _uiState.value = _uiState.value.copy(
                    flight = flights,
                    status = LoadStatus.Success()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }

}
