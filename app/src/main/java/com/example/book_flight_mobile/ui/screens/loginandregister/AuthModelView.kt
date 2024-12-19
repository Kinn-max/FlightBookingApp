package com.example.book_flight_mobile.ui.screens.loginandregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.models.UserResponse
import com.example.book_flight_mobile.repositories.MainLog
import com.example.book_flight_mobile.repositories.UserRepository
import com.example.book_flight_mobile.ui.screens.search.SearchUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class UserUiState(
    val userLogin:UserResponse?,
    val userRegister:UserResponse?,
    val status: LoadStatus = LoadStatus.Innit(),
)
@HiltViewModel
class AuthModelView@Inject constructor (
    private val log: MainLog?,
    private val userRepository: UserRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun  reset(){
        _uiState.value = _uiState.value.copy(status = LoadStatus.Innit())
    }
    fun login(userName: String, password:String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                val user = userRepository.login(userName,password)
//                _uiState.value = _uiState.value.copy(
//                    flights =
//                    status = LoadStatus.Success()
//                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error(e.message ?: "Unknown error"))
            }
        }
    }
}
