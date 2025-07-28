package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import com.example.stockmanagementapp.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


data class LoginState(
    val username: String = "",
    val password: String = "",
    val isValidated: Boolean = false,
    val error: String? = null
)

sealed class LoginAction {
    data class ValidateUsername(val username: String) : LoginAction()
    data class ValidatePassword(val password: String) : LoginAction()
    data object NavigateToDashboard : LoginAction()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.NavigateToDashboard -> {}
            is LoginAction.ValidatePassword -> {}
            is LoginAction.ValidateUsername -> {}
        }
    }
}