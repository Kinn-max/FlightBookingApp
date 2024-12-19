package com.example.book_flight_mobile.ui.screens.ticket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.FlightResponse
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.models.UserResponse
import com.example.book_flight_mobile.repositories.FlightRepository
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.TicketRepository
import com.example.book_flight_mobile.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TicketUiState(
    val user: UserResponse? = null ,
    val ticket: TicketBookedInfo? = null,
    val flight: FlightResponse? = null,
    val status: LoadStatus = LoadStatus.Innit(),
    val ticketList: List<TicketBookedInfo> = emptyList()
)

@HiltViewModel
class TicketModelView @Inject constructor(
    private val log: MainLog?,
    private val flightRepository: FlightRepository?,
    private val ticketRepository: TicketRepository?,
    private val userRepository: UserRepository?,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicketUiState())
    val uiState = _uiState.asStateFlow()

    fun reset() {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Innit())
    }
    fun getFlightById(id:Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val flight = flightRepository?.getFlightById(id)
                if (flight != null) {
                    _uiState.value = _uiState.value.copy(flight = flight, status = LoadStatus.Success())
                } else {
                    _uiState.value = _uiState.value.copy(
                        status = LoadStatus.Error("Flight is null")
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }
    fun getTicketById(id:Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val ticket = ticketRepository?.getTicketById(id)
                if (ticket != null) {
                    _uiState.value = _uiState.value.copy(ticket = ticket, status = LoadStatus.Success())
                } else {
                    _uiState.value = _uiState.value.copy(
                        status = LoadStatus.Error("Ticket error")
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }
    fun getUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val user = userRepository?.loadUserInfo()
                if (user != null) {
                    _uiState.value = _uiState.value.copy(user = user, status = LoadStatus.Success())
                } else {
                    _uiState.value = _uiState.value.copy(
                        status = LoadStatus.Error("User is null")
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }
}