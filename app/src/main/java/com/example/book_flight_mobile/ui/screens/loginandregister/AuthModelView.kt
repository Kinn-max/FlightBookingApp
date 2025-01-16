package com.example.book_flight_mobile.ui.screens.loginandregister

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.book_flight_mobile.Screen
import com.example.book_flight_mobile.common.enum.LoadStatus
import com.example.book_flight_mobile.config.TokenManager
import com.example.book_flight_mobile.models.UserLogin
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
    val username: String = "",
    val password: String = "",
    val userRegister:UserResponse?=null,
    val userId:Long?= -1,
    val status: LoadStatus = LoadStatus.Innit(),
)
@HiltViewModel
class AuthModelView@Inject constructor (
    private val log: MainLog?,
    private val userRepository: UserRepository,
    private val tokenManager: TokenManager,
): ViewModel(){
    private val _uiState = MutableStateFlow(UserUiState())
    val uiState = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun  reset(){
        _uiState.value = _uiState.value.copy(status = LoadStatus.Innit())
    }
    fun login(navController: NavHostController) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = LoadStatus.Loading())
            try {
                var userLogin = UserLogin(uiState.value.username, uiState.value.password)
                val result = userRepository?.login(userLogin)
                result?.token?.let {
                    tokenManager.saveToken(it)
                }
                result?.user?.name?.let {
                    tokenManager.saveUserName(it)
                }
                _uiState.value = _uiState.value.copy( status = LoadStatus.Success())
                navController.navigate(Screen.Profile.route)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(status = LoadStatus.Error("Tài khoản hoặc mật khẩu không đúng!"))
            }
        }
    }

}
