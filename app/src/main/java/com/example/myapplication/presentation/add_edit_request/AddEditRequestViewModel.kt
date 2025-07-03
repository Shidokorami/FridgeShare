package com.example.myapplication.presentation.add_edit_request

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.model.exception.InvalidProductException
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.useCases.household_use_cases.HouseholdUseCases
import com.example.myapplication.domain.useCases.request_use_cases.RequestUseCases
import com.example.myapplication.domain.useCases.user_use_cases.UserUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.text.isNotBlank

@HiltViewModel
class AddEditRequestViewModel @Inject constructor(
    private val requestUseCases: RequestUseCases,
    private val householdUseCases: HouseholdUseCases,
    private val userUseCases: UserUseCases,
    private val authRepository: AuthRepository,

    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _requestName = MutableStateFlow("")
    val requestName: StateFlow<String> = _requestName.asStateFlow()

    private val _requestQuantity = MutableStateFlow("")
    val requestQuantity: StateFlow<String> = _requestQuantity.asStateFlow()

    private val _requestUnit = MutableStateFlow<String?>(null)
    val requestUnit: StateFlow<String?> = _requestUnit.asStateFlow()

    private val _requestFulfilledDate = MutableStateFlow<Long?>(null)
    val requestFulfilledDate: StateFlow<Long?> = _requestFulfilledDate.asStateFlow()

    private val _requestPrice = MutableStateFlow<String?>(null)
    val requestPrice: StateFlow<String?> = _requestPrice.asStateFlow()

    private val _isUnitDropdownExpanded = MutableStateFlow(false)
    val isUnitDropdownExpanded: StateFlow<Boolean> = _isUnitDropdownExpanded.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentRequestId: String? = null

    private var currentCreatorId: String? = null

    private var currentCreatorName: String? = null

    private var currentUserId: String? = null

    private var currentUserName: String? = null

    private val householdId: String = savedStateHandle.get<String>("householdId") ?: ""

    private val _householdName = MutableStateFlow("")
    val householdName: StateFlow<String> = _householdName.asStateFlow()

    private val _isEditing = MutableStateFlow(false)
    val isEditing: StateFlow<Boolean> = _isEditing.asStateFlow()

    private val _showDeleteConfirmationDialog = MutableStateFlow(false)
    val showDeleteConfirmationDialog: StateFlow<Boolean> = _showDeleteConfirmationDialog

    init {
        viewModelScope.launch {
            authRepository.currentUserId.collectLatest {userId ->
                if (userId != null) {
                    currentUserId = userId
                }
            }
        }


        savedStateHandle.get<String>("productId")?.let { requestId ->
            if (requestId.isNotBlank()) {
                _isEditing.value = true
                viewModelScope.launch {
                    requestUseCases.getRequest(householdId, requestId).firstOrNull()?.also { request ->
                        currentRequestId = request.id
                        currentCreatorId = request.creatorId
                        currentUserName = request.creatorName
                        _requestName.value = request.name
                        _requestQuantity.value = request.quantity?.toPlainString() ?: ""
                        _requestUnit.value = request.unit
                        _requestFulfilledDate.value = request.fulfilledDate
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
    fun onEvent(event: AddEditRequestEvent) {
        when (event) {
            is AddEditRequestEvent.EnteredName -> {
                _requestName
                    .value = event.value
            }
            is AddEditRequestEvent.EnteredQuantity -> {
                _requestQuantity.value = event.value
            }
            is AddEditRequestEvent.ChangeUnitSelection -> {
                _requestUnit.value = event.unit
                _isUnitDropdownExpanded.value = false
            }
            is AddEditRequestEvent.ChangeUnitDropdownExpanded -> {
                _isUnitDropdownExpanded.value = event.expanded
            }
            is AddEditRequestEvent.ChangeExpirationDate -> {
                //_requestExpirationDate.value = event.date
            }
            is AddEditRequestEvent.SaveProduct -> {
                viewModelScope.launch {
                    try {

                        if (_requestName
                            .value.isBlank()) {
                            throw InvalidProductException("Product name cannot be empty.")
                        }

                        val quantityBigDecimal = _requestQuantity.value.toBigDecimalOrNull()
                        val currCreatorId = (currentCreatorId?: currentUserId).toString()
                        val currCreatorName = currentUserName?:userUseCases.getUserName(currentUserId)

                        requestUseCases.addRequest(
                            householdId = householdId,
                            request = ProductRequest(
                                id = currentRequestId?: "",
                                householdId = householdId,
                                creatorId = currCreatorId ,
                                creatorName = currCreatorName,
                                name = requestName.value,
                                quantity = quantityBigDecimal,
                                unit = requestUnit.value,
                                fulfilled = false,
                                moneyReturned = false
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveRequestSuccess)
                    } catch (e: InvalidProductException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Could not save request"
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

            is AddEditRequestEvent.DeleteProduct -> {
                viewModelScope.launch {
                    currentRequestId?.let { id ->
                        requestUseCases.deleteRequest(householdId, id)
                        _showDeleteConfirmationDialog.value = false
                        _eventFlow.emit(UiEvent.DeleteRequestSuccess)
                    }
                }
            }

            is AddEditRequestEvent.ShowDeleteConfirmation -> {
                _showDeleteConfirmationDialog.value = true
            }

            is AddEditRequestEvent.HideDeleteConfirmation -> {
                _showDeleteConfirmationDialog.value = false
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveRequestSuccess : UiEvent()
        object DeleteRequestSuccess : UiEvent()
    }
}