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

data class DashboardState(
    val lowStockItems: List<String> = emptyList(),
    val recentTransactions: List<String> = emptyList(),
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
    private val repository: StockRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState

    fun onAction(action: DashboardAction) {
        when (action) {
            DashboardAction.Init -> {}
            DashboardAction.NavigateToProductList -> {
                viewModelScope.launch {
                    navigator.navigateTo(Destination.ProductList)
                }
            }
            DashboardAction.NavigateToStockManagement -> {}
            DashboardAction.NavigateToSupplierList -> {}
            DashboardAction.NavigateToTransactionHistory -> {}
        }
    }
}