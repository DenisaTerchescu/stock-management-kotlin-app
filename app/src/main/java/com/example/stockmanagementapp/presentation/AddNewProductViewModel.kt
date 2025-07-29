package com.example.stockmanagementapp.presentation

import androidx.lifecycle.ViewModel
import com.example.stockmanagementapp.data.model.Product
import com.example.stockmanagementapp.repository.StockRepository
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

data class AddNewProductState(
    val product: Product? = createEmptyProduct(),
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

sealed class AddNewProductAction {

    data class SaveProduct(val product: Product) : AddNewProductAction()
}

@HiltViewModel
class AddNewProductViewModel @Inject constructor(
    private val repository: StockRepository, private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddNewProductState(isLoading = true))
    val uiState: StateFlow<AddNewProductState> = _uiState

    fun onAction(action: AddNewProductAction) {
        when (action) {

            is AddNewProductAction.SaveProduct -> {

            }
        }
    }


}