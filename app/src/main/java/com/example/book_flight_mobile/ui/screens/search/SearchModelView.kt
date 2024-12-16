package com.example.book_flight_mobile.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightRequest
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.repositories.FlightRepository
import com.example.book_flight_mobile.repositories.MainLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val flights:List<FlightResponse> = emptyList(),
    val status:LoadStatus = LoadStatus.Innit(),
    val flightSearch:List<FlightResponse> = emptyList(),
)
@HiltViewModel
class SearchModelView @Inject constructor (
    private val log: MainLog?,
    private val flightRepository: FlightRepository
):ViewModel(){
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()
    init {
        loadSearchBase()
    }
    fun  reset(){
        _uiState.value = _uiState.value.copy(status = LoadStatus.Innit())
    }
     fun loadSearchBase() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val flights = flightRepository.loadFlightHome()
                _uiState.value = _uiState.value.copy(
                    flights = flights,
                    status = LoadStatus.Success()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }
    fun searchRequest(flightRequest: FlightRequest) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val flights = flightRepository.searchFlight(flightRequest)
                _uiState.value = _uiState.value.copy(
                    flightSearch = flights,
                    status = LoadStatus.Success()
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }

}