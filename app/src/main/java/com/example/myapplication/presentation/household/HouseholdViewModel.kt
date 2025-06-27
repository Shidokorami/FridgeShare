package com.example.myapplication.presentation.household

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.repository.OfflineProductRepository
import com.example.myapplication.domain.useCases.product_use_cases.ProductUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@HiltViewModel
class HouseholdViewModel @Inject constructor(
    private val productUseCases: ProductUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableStateFlow(HouseholdStates())
    val state: StateFlow<HouseholdStates> = _state



    private var getProductsJob: Job? = null

    private val householdId: Long = savedStateHandle.get<Long>("householdId") ?: 0L

    init {
        getProducts()
    }

    fun getProducts(){
        getProductsJob?.cancel()
        getProductsJob = productUseCases.getProductsByHousehold(this.householdId)
            .onEach { products->
                _state.value = _state.value.copy(
                    products = products,
                    isLoading = false
                )
            }
            .launchIn(viewModelScope)
    }
}