package com.example.myapplication.presentation.requestlist

import RequestListEvent
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.repository.ProductRequestRepository
import com.example.myapplication.domain.useCases.request_use_cases.RequestUseCases
import com.example.myapplication.presentation.add_edit_product.AddEditProductViewModel.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val requestUseCases: RequestUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val householdId: String = savedStateHandle.get<String>("householdId")?: throw IllegalArgumentException("HouseholdId is required")

    private val _unfulfilledRequests = MutableStateFlow<List<ProductRequest>>(emptyList())
    val unfulfilledRequests: StateFlow<List<ProductRequest>> = _unfulfilledRequests.asStateFlow()

    private val _fulfilledRequests = MutableStateFlow<List<ProductRequest>>(emptyList())
    val fulfilledRequests: StateFlow<List<ProductRequest>> = _fulfilledRequests.asStateFlow()

    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private var getUnfulfilledRequestsJob: Job? = null
    private var getFulfilledRequestsJob: Job? = null



    init {
        viewModelScope.launch {
            getUnfulfilledRequests()
            getFulfilledRequests()
        }
    }

    private suspend fun getUnfulfilledRequests() {
        getUnfulfilledRequestsJob?.cancel()
        getUnfulfilledRequestsJob = requestUseCases.getRequestsByFulfill(householdId, false)
            .onEach { requests ->
                _unfulfilledRequests.value = requests
            }
            .launchIn(viewModelScope)
    }

    private suspend fun getFulfilledRequests() {
        getFulfilledRequestsJob?.cancel()
        getFulfilledRequestsJob = requestUseCases.getRequestsByFulfill(householdId, true)
            .onEach { requests ->
                _fulfilledRequests.value = requests
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: RequestListEvent) {
        when (event) {
            is RequestListEvent.ChangeSelectedTab -> {
                _selectedTabIndex.value = event.newIndex
            }
            is RequestListEvent.OnDeleteRequestClick -> {
                viewModelScope.launch {
                    requestUseCases.deleteRequest(householdId, event.requestId)
                }
            }
            is RequestListEvent.OnFulfillRequestClick -> {
                viewModelScope.launch {
                    // Możesz usunąć ten event, jeśli nie jest już potrzebny
                }
            }
            is RequestListEvent.OnConfirmFulfillRequest -> {
                viewModelScope.launch {
                    requestUseCases.fulfillRequest(event.updatedRequest, householdId)
                    // Zakładam, że masz w UseCase metodę fulfillRequest która przyjmuje cały obiekt ProductRequest
                }
            }
        }
    }

}