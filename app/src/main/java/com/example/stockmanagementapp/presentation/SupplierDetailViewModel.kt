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

data class SupplierDetailState(
    val supplier: Supplier? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)

sealed class SupplierDetailAction {
    data class Init(val supplierId: Int) : SupplierDetailAction()

    data class SaveChanges(
        val name: String, val email: String, val phone: String
    ) : SupplierDetailAction()
}

@HiltViewModel
class SupplierDetailViewModel @Inject constructor(
    private val repository: StockRepository, private val navigator: Navigator
) : ViewModel() {

    private val _uiState = MutableStateFlow(SupplierDetailState(isLoading = true))
    val uiState: StateFlow<SupplierDetailState> = _uiState

    fun onAction(action: SupplierDetailAction) {
        when (action) {

            is SupplierDetailAction.Init -> {
                viewModelScope.launch {
                    repository.getSupplierById(action.supplierId).catch {
                        _uiState.value = _uiState.value.copy(error = it.message)
                    }.collect {
                        it?.let {
                            _uiState.value = _uiState.value.copy(supplier = it)
                        }

                    }
                }
            }


            is SupplierDetailAction.SaveChanges -> {
                val existingSupplier = _uiState.value.supplier
                val updatedSupplier= existingSupplier?.copy(
                    name = action.name.ifBlank { existingSupplier.name },
                    email = action.email.ifBlank { existingSupplier.email },
                    phone = action.phone.ifBlank { existingSupplier.phone })

                viewModelScope.launch {
                    updatedSupplier?.let {
                        repository.updateSupplier(updatedSupplier)
                    }
                    navigator.navigateTo(Destination.SupplierList.route)

                }
            }
        }
    }


}