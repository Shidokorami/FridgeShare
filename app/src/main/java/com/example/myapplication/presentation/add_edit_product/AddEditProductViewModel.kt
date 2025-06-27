package com.example.myapplication.presentation.add_edit_product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.useCases.product_use_cases.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.myapplication.domain.model.exception.InvalidProductException
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import java.math.BigDecimal


@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val useCases: ProductUseCases,
    savedStateHandle: SavedStateHandle

): ViewModel() {

    private val _productName = MutableStateFlow("") // String dla TextField
    val productName: StateFlow<String> = _productName.asStateFlow()

    private val _productQuantity = MutableStateFlow("") // String dla TextField
    val productQuantity: StateFlow<String> = _productQuantity.asStateFlow()

    private val _productUnit = MutableStateFlow<String?>(null)
    val productUnit: StateFlow<String?> = _productUnit.asStateFlow()

    private val _productExpirationDate = MutableStateFlow<Long?>(null)
    val productExpirationDate: StateFlow<Long?> = _productExpirationDate.asStateFlow()

    private val _isUnitDropdownExpanded = MutableStateFlow(false)
    val isUnitDropdownExpanded: StateFlow<Boolean> = _isUnitDropdownExpanded.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentProductId: Long? = null

    private val householdId: Long = savedStateHandle.get<Long>("householdId") ?: 0L

    init {
        savedStateHandle.get<Long>("productId")?.let { productId ->
            if (productId != -1L) {
                viewModelScope.launch {
                    useCases.getProduct(productId)?.also { product ->
                        currentProductId = product.id
                        _productName.value = product.name
                        _productQuantity.value = product.quantity?.toPlainString() ?: ""
                        _productUnit.value = product.unit
                        _productExpirationDate.value = product.expirationDate
                    }
                }
            }
        }
    }

    // --- Pojedynczy punkt wejścia dla wszystkich zdarzeń UI ---
    fun onEvent(event: AddEditProductEvent) {
        when (event) {
            is AddEditProductEvent.EnteredName -> {
                _productName.value = event.value
            }
            is AddEditProductEvent.EnteredQuantity -> {
                _productQuantity.value = event.value
            }
            is AddEditProductEvent.ChangeUnitSelection -> {
                _productUnit.value = event.unit
                _isUnitDropdownExpanded.value = false
            }
            is AddEditProductEvent.ChangeUnitDropdownExpanded -> {
                _isUnitDropdownExpanded.value = event.expanded
            }
            is AddEditProductEvent.ChangeExpirationDate -> {
                _productExpirationDate.value = event.date
            }
            is AddEditProductEvent.SaveProduct -> {
                viewModelScope.launch {
                    try {

                        val quantityBigDecimal = _productQuantity.value.toBigDecimalOrNull()

                        if (_productName.value.isBlank()) {
                            throw InvalidProductException("Product name cannot be empty.")
                        }
                        if (quantityBigDecimal == null || quantityBigDecimal <= BigDecimal.ZERO) {
                            throw InvalidProductException("Quantity must be higher than zero")
                        }

                        useCases.addProduct(
                            Product(
                                id = currentProductId,
                                name = _productName.value,
                                quantity = quantityBigDecimal,
                                unit = _productUnit.value,
                                expirationDate = _productExpirationDate.value,
                                householdId = householdId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveProductSuccess)
                    } catch (e: InvalidProductException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Could not save product"
                            )
                        )
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = "Unexpected error: ${e.localizedMessage}"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveProductSuccess : UiEvent()
    }

}

