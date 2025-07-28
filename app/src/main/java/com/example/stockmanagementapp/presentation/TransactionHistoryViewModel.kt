package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementapp.data.model.Transaction
import com.example.stockmanagementapp.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.List

data class TransactionHistoryState(
    val transactions: List<Transaction> = emptyList(),
    val saleFilterChipSelected: Boolean = false,
    val restockFilterChipSelected: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class TransactionHistoryAction {
    data object Init : TransactionHistoryAction()
    data class FilterTransactionsBy(val type: TransactionType?) : TransactionHistoryAction()
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

    private var initialTransactionList = emptyList<Transaction>()

    fun onAction(action: TransactionHistoryAction) {
        when (action) {
            TransactionHistoryAction.Init -> {
                viewModelScope.launch {
                    repository.getAllTransactions()
                        .catch {
                            _uiState.value = _uiState.value.copy(error = it.message)
                        }
                        .collect { transactions ->
                            _uiState.value = _uiState.value.copy(transactions = transactions)
                            initialTransactionList = transactions
                        }

                }
            }
            is TransactionHistoryAction.FilterTransactionsBy -> {
                initialTransactionList =  _uiState.value.transactions
                when(action.type){
                    TransactionType.SALE ->
                        _uiState.value = _uiState.value.copy(transactions = initialTransactionList.filter { it.type == "sale" })
                    TransactionType.RESTOCK ->
                        _uiState.value = _uiState.value.copy(transactions = initialTransactionList.filter { it.type == "restock" })

                    null ->  _uiState.value = _uiState.value.copy(transactions = initialTransactionList)
                }

            }

        }
    }

}