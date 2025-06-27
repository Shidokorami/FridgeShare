package com.example.myapplication.presentation.householddetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.useCases.household_use_cases.HouseholdUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HouseholdDetailsViewModel @Inject constructor(
    private val useCases: HouseholdUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {


    private val _householdName = MutableStateFlow("")
    var householdName: StateFlow<String> = _householdName.asStateFlow()

    private val householdId: Long = savedStateHandle.get<Long>("householdId") ?: 0L



    init {
        if (householdId != -1L) {
    loadHouseholdDetails()
        }
    }

    private fun loadHouseholdDetails() {
        viewModelScope.launch{
            useCases.getHousehold(householdId)?.also { household ->
                _householdName.value = household.name
            }
        }

    }
}