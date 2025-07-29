package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


data class LoginState(
    val username: String = "",
    val password: String = "",
    val error: String? = null
)

sealed class LoginAction {
    data class ValidateUsername(val username: String) : LoginAction()
    data class ValidatePassword(val password: String) : LoginAction()
    data object NavigateToDashboard : LoginAction()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: StockRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.NavigateToDashboard -> {
                val username =  _uiState.value.username
                val password =  _uiState.value.password
                if (username == "admin" && password == "admin"){
                    viewModelScope.launch {
                        navigator.navigateTo(Destination.Dashboard.route)
                    }
                }
                else {
                    _uiState.value = _uiState.value.copy(
                        error = "Incorrect credentials!",
                    )
                }

            }
            is LoginAction.ValidatePassword -> {

                _uiState.value = _uiState.value.copy(
                    password = action.password,
                    error = null
                )
            }
            is LoginAction.ValidateUsername -> {
                _uiState.value = _uiState.value.copy(
                    username = action.username,
                    error = null
                )
            }
        }
    }
}