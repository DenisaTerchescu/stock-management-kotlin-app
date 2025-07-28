package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import com.example.stockmanagementapp.data.model.Transaction
import com.example.stockmanagementapp.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class TransactionHistoryState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TransactionHistoryAction {
    data object Init : TransactionHistoryAction()
    data class FilterTransactionsBy(val type: TransactionType) : TransactionHistoryAction()
}

enum class TransactionType {
    SALE, RESTOCK
}

@HiltViewModel
class TransactionHistoryViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TransactionHistoryState(isLoading = true))
    val uiState: StateFlow<TransactionHistoryState> = _uiState

    fun onAction(action: TransactionHistoryAction) {
        when (action) {
            is TransactionHistoryAction.FilterTransactionsBy -> {}
            TransactionHistoryAction.Init -> {}
        }
    }

}