package com.example.myapplication.presentation.householdlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.useCases.household_use_cases.HouseholdUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@HiltViewModel
class HouseholdListViewModel @Inject constructor(
    private val householdUseCases: HouseholdUseCases,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HouseholdListStates())
    val state: StateFlow<HouseholdListStates> = _state

    private var getHouseholdsJob: Job? = null

    init {
        viewModelScope.launch {
            authRepository.currentUserId.collectLatest { userId ->
                if (userId != null) {
                    getHouseholds(userId)
                } else {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        households = emptyList()
                    )
                }
            }
        }
    }

    fun onEvent(event: HouseholdListEvent){
        when(event){
            is HouseholdListEvent.ClickedCreateButton -> {

                viewModelScope.launch {
                    householdUseCases.createHousehold(event.value)
                }

            }
        }
    }

    fun getHouseholds(userId: String){
        getHouseholdsJob?.cancel()
        getHouseholdsJob = householdUseCases.getHouseholds(userId)
            .onEach { households ->
                _state.value = _state.value.copy(
                    households = households,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}