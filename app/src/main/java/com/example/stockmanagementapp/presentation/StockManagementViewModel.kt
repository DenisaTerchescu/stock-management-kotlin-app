package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementapp.data.model.Transaction
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StockManagementState(
    val transaction: Transaction = createEmptyTransaction(),
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    companion object EmptyTransaction {
        fun createEmptyTransaction(): Transaction = Transaction(
            id = 0, date = System.currentTimeMillis(), type = "", productId = 0, quantity = 0, notes = ""
        )
    }
}

sealed class StockManagementAction {

    data class Save(val transaction: Transaction) : StockManagementAction()
}

@HiltViewModel
class StockManagementViewModel @Inject constructor(
    private val repository: StockRepository, private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockManagementState(isLoading = true))
    val uiState: StateFlow<StockManagementState> = _uiState

    fun onAction(action: StockManagementAction) {
        when (action) {

            is StockManagementAction.Save -> {
                viewModelScope.launch {
                    repository.insertTransaction(action.transaction)
                    navigator.navigateTo(Destination.Dashboard.route)
                }
            }
        }
    }


}