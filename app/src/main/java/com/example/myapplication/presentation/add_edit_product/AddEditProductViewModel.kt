package com.example.myapplication.presentation.add_edit_product

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.model.exception.InvalidProductException
import com.example.myapplication.domain.useCases.household_use_cases.HouseholdUseCases
import com.example.myapplication.domain.useCases.product_use_cases.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddEditProductViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    private val householdUseCases: HouseholdUseCases,
    savedStateHandle: SavedStateHandle

): ViewModel() {

    private val _productName = MutableStateFlow("")
    val productName: StateFlow<String> = _productName.asStateFlow()

    private val _productQuantity = MutableStateFlow("")
    val productQuantity: StateFlow<String> = _productQuantity.asStateFlow()

    private val _productUnit = MutableStateFlow<String?>(null)
    val productUnit: StateFlow<String?> = _productUnit.asStateFlow()

    private val _productExpirationDate = MutableStateFlow<Long?>(null)
    val productExpirationDate: StateFlow<Long?> = _productExpirationDate.asStateFlow()

    private val _isUnitDropdownExpanded = MutableStateFlow(false)
    val isUnitDropdownExpanded: StateFlow<Boolean> = _isUnitDropdownExpanded.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentProductId: String? = null

    private val householdId: String = savedStateHandle.get<String>("householdId") ?: ""

    private val _householdName = MutableStateFlow("")
    val householdName: StateFlow<String> = _householdName.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing.asStateFlow()

    private val _showDeleteConfirmationDialog = MutableStateFlow(false)
    val showDeleteConfirmationDialog: StateFlow<Boolean> = _showDeleteConfirmationDialog

    init {
        savedStateHandle.get<String>("productId")?.let { productId ->
            if (productId.isNotBlank()) {
                _isEditing.value = true
                viewModelScope.launch {
                    productUseCases.getProduct(householdId, productId).firstOrNull()?.also { product ->
                        currentProductId = product.id
                        _productName.value = product.name
                        _productQuantity.value = product.quantity?.toPlainString() ?: ""
                        _productUnit.value = product.unit
                        _productExpirationDate.value = product.expirationDate
                    }
                }
            }
        }

        if (householdId.isNotBlank()){
            viewModelScope.launch {
                householdUseCases.getHousehold(householdId).firstOrNull()?.also{ household ->
                    _householdName.value = household.name
                }
            }
        }
    }
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

                        productUseCases.addProduct(
                            householdId = householdId,
                            product = Product(
                                id = currentProductId ?: "",
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

            is AddEditProductEvent.DeleteProduct -> {
                viewModelScope.launch {
                    currentProductId?.let { id ->
                        productUseCases.deleteProduct(householdId, Product(id = id, name = "", householdId = householdId))
                        _showDeleteConfirmationDialog.value = false
                        _eventFlow.emit(UiEvent.DeleteProductSuccess)
                    }
                }
            }

            is AddEditProductEvent.ShowDeleteConfirmation -> {
                _showDeleteConfirmationDialog.value = true
            }

            is AddEditProductEvent.HideDeleteConfirmation -> {
                _showDeleteConfirmationDialog.value = false
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveProductSuccess : UiEvent()
        object DeleteProductSuccess : UiEvent()
    }
}