package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Destination
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class ProductDetailState(
    val product: Product = createEmptyProduct(),
    val isLoading: Boolean = false,
    val error: String? = null
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

    data class NavigateToEditProduct(val productId: Int) : ProductDetailAction()
}

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val repository: StockRepository, private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailState(isLoading = true))
    val uiState: StateFlow<ProductDetailState> = _uiState

    fun onAction(action: ProductDetailAction) {
        when (action) {

            is ProductDetailAction.Init -> {}

            is ProductDetailAction.NavigateToEditProduct -> {
                navigator.navigateTo(Destination.ProductDetail.createRoute(action.productId))
            }
        }
    }


}