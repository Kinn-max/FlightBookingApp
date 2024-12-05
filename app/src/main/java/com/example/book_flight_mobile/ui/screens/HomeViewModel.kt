package com.example.book_flight_mobile.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.AirportResponse
import com.example.book_flight_mobile.repositories.AirportRepository
import com.example.book_flight_mobile.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val notes:List<AirportResponse> = emptyList(),
    val status:LoadStatus = LoadStatus.Innit(),
    val selectedIndex:Int = -1
)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val log: MainLog?,
    private val airportRepository: AirportRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAirports()
    }

    private fun loadAirports() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val airports = airportRepository.loadAirport()
                _uiState.value = _uiState.value.copy(
                    notes = airports,
                    status = LoadStatus.Success()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }
}
