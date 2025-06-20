package com.example.myapplication.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.preferences.UserPreferences
import com.example.myapplication.domain.model.Household
import com.example.myapplication.domain.useCases.HouseholdUseCases
import com.example.myapplication.presentation.viewmodel.states.HouseholdListStates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HouseholdListViewModel @Inject constructor(
    private val householdUseCases: HouseholdUseCases,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val _state = MutableStateFlow(HouseholdListStates())
    val state: StateFlow<HouseholdListStates> = _state

    private var getHouseholdsJob: Job? = null

    init {
        getHouseholds()
    }

    fun getHouseholds(){
        getHouseholdsJob?.cancel()
        getHouseholdsJob = householdUseCases.getHouseholds()
            .onEach { households ->
                _state.value = _state.value.copy(
                    households = households,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}