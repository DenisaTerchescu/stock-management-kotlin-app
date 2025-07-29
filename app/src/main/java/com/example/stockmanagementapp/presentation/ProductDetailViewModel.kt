package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    companion object EmptyProduct {
        fun createEmptyProduct(): Product = Product(
            id = 0,
            name = "",
            description = "",
            price = 0.0,
            category = "",
            barcode = "",
            supplierId = 0,
            currentStockLevel = 0,
            minimumStockLevel = 0
        )
    }
}

sealed class ProductDetailAction {
    data class Init(val productId: Int) : ProductDetailAction()

    data class SaveChanges(
        val name: String, val description: String, val category: String
    ) : ProductDetailAction()
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: StockRepository, private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailState(isLoading = true))
    val uiState: StateFlow<ProductDetailState> = _uiState

    fun onAction(action: ProductDetailAction) {
        when (action) {

            is ProductDetailAction.Init -> {
                viewModelScope.launch {

                    repository.getProductById(action.productId).catch {
                        _uiState.value = _uiState.value.copy(error = it.message)
                    }.collect {
                        it?.let {
                            _uiState.value = _uiState.value.copy(product = it)
                        }

                    }


                }
            }


            is ProductDetailAction.SaveChanges -> {
                val existingProduct = _uiState.value.product
                val updatedProduct = existingProduct?.copy(
                    name = action.name.ifBlank { existingProduct.name },
                    description = action.description.ifBlank { existingProduct.description },
                    category = action.category.ifBlank { existingProduct.category })

                viewModelScope.launch {
                    updatedProduct?.let {
                        repository.updateProduct(it)
                    }
                    navigator.navigateTo(Destination.ProductList.route)

                }
            }
        }
    }


}