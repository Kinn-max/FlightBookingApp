package com.example.book_flight_mobile.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.TicketBookedInfo
import com.example.book_flight_mobile.models.UserResponse
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.TicketRepository
import com.example.book_flight_mobile.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProfileUiState(
    val info: UserResponse? = null,
    val status: LoadStatus = LoadStatus.Innit(),
    val ticketList: List<TicketBookedInfo> = emptyList()
)

@HiltViewModel
class ProfileModelView @Inject constructor(
    private val log: MainLog?,
    private val userRepository: UserRepository?,
    private val ticketRepository: TicketRepository?
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState = _uiState.asStateFlow()

    fun reset() {
        _uiState.value = _uiState.value.copy(status = LoadStatus.Innit())
    }

    fun personalInformation() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val info = userRepository?.loadUserInfo()
                if (info != null) {
                    _uiState.value = _uiState.value.copy(info = info,  ticketList = info.bookingList, status = LoadStatus.Success())
                } else {
                    _uiState.value = _uiState.value.copy(
                        status = LoadStatus.Error("Bạn chưa đăng nhập!")
                    )
                }
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }

}
