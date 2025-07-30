package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementapp.data.model.Supplier
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SupplierListState(
    val supplierList: List<Supplier> = emptyList(),
    val searchValue: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class SupplierListAction {
    data object FetchSuppliers : SupplierListAction()

    data class NavigateToSupplierDetailScreen(val supplierId: Int) : SupplierListAction()
    data class SearchSupplier(val searchValue: String) : SupplierListAction()
}

@HiltViewModel
class SupplierListViewModel @Inject constructor(
    private val repository: StockRepository, private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(SupplierListState(isLoading = true))
    val uiState: StateFlow<SupplierListState> = _uiState

    private val _suppliers = MutableStateFlow<List<Supplier>>(emptyList())
    val suppliers: StateFlow<List<Supplier>> = _suppliers

    fun onAction(action: SupplierListAction) {
        when (action) {

            SupplierListAction.FetchSuppliers -> {
                fetchSuppliers()
            }

            is SupplierListAction.NavigateToSupplierDetailScreen -> {
                navigator.navigateTo(Destination.SupplierDetail.createRoute(action.supplierId))
            }

            is SupplierListAction.SearchSupplier -> {
                val searchValue = action.searchValue
                _uiState.value = _uiState.value.copy(searchValue = searchValue)
                if (searchValue.isNotBlank()) {
                    _uiState.value = _uiState.value.copy(supplierList = _suppliers.value.filter {
                        it.name.toLowerCase().contains(searchValue.toLowerCase())
                    })
                } else {
                    _uiState.value = _uiState.value.copy(supplierList = _suppliers.value)
                }
            }
        }
    }

    private fun fetchSuppliers() {
        viewModelScope.launch {
            repository.getAllSuppliers().catch {
                _uiState.value = _uiState.value.copy(error = it.message)
            }.collect {
                _uiState.value = _uiState.value.copy(supplierList = it)
                _suppliers.value = it
            }

        }
    }

}