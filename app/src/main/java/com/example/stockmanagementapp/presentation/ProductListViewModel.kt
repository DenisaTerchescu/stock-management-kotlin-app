package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductListState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

sealed class ProductListAction {
    data class AddProduct(val product: Product) : ProductListAction()
    data object FetchProducts : ProductListAction()
}

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListState(isLoading = true))
    val uiState: StateFlow<ProductListState> = _uiState

    fun onAction(action: ProductListAction) {
        when (action) {

            ProductListAction.FetchProducts -> {
                fetchProducts()
            }

            is ProductListAction.AddProduct -> {
                viewModelScope.launch {
                    repository.insertProduct(action.product)
                    fetchProducts()
                }
            }
        }
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            repository.getAllProducts()
                .catch {
                    _uiState.value = _uiState.value.copy(error = it.message)
                }
                .collect { products ->
                    _uiState.value = _uiState.value.copy(products = products)
                }

        }
    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            repository.insertProduct(product)
            fetchProducts()
        }
    }
}