package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.data.model.Transaction
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardState(
    val lowStockItems: List<Product> = emptyList(),
    val recentTransactions: List<Transaction> = emptyList(),
    val error: String? = null
)

sealed class DashboardAction {
    data object Init : DashboardAction()
    data object NavigateToProductList : DashboardAction()
    data object NavigateToSupplierList : DashboardAction()
    data object NavigateToTransactionHistory : DashboardAction()
    data object NavigateToStockManagement : DashboardAction()
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: StockRepository, private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState


    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.Init -> {
                fetchLowStockItems()
                fetchRecentTransactions()

            }

            DashboardAction.NavigateToProductList -> {
                viewModelScope.launch {
                    navigator.navigateTo(Destination.ProductList.route)
                }
            }

            DashboardAction.NavigateToStockManagement -> {}
            DashboardAction.NavigateToSupplierList -> {}
            DashboardAction.NavigateToTransactionHistory -> {
                viewModelScope.launch {
                    navigator.navigateTo(Destination.TransactionHistory.route)
                }
            }
        }
    }

    private fun fetchLowStockItems() {
        viewModelScope.launch {
            repository.getLowStockProducts().catch {
                    _uiState.value = _uiState.value.copy(error = it.message)
                }.collect { products ->
                    _uiState.value = _uiState.value.copy(lowStockItems = products)
                }

        }
    }

    private fun fetchRecentTransactions() {
        viewModelScope.launch {
            repository.getRecentTransactions(3).catch {
                    _uiState.value = _uiState.value.copy(error = it.message)
                }.collect { recentTransactions ->
                    _uiState.value = _uiState.value.copy(recentTransactions = recentTransactions)
                }

        }
    }
}