package com.example.myapplication.presentation.requestlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.repository.ProductRequestRepository
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
import javax.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val requestRepository: ProductRequestRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val householdId: Long = savedStateHandle.get<Long>("householdId")?: throw IllegalArgumentException("HouseholdId is required")

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
        getUnfulfilledRequests()
        getFulfilledRequests()
    }

    private fun getUnfulfilledRequests() {
        getUnfulfilledRequestsJob?.cancel()
        getUnfulfilledRequestsJob = requestRepository.getRequestByFulfillForHousehold(householdId, false)
            .onEach { requests ->
                _unfulfilledRequests.value = requests
            }
            .launchIn(viewModelScope)
    }

    private fun getFulfilledRequests() {
        getFulfilledRequestsJob?.cancel()
        getFulfilledRequestsJob = requestRepository.getRequestByFulfillForHousehold(householdId, true)
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
        }
    }
}