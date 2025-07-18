package com.example.myapplication.presentation.householduserlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.User
import com.example.myapplication.domain.useCases.user_use_cases.UserUseCases
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
class HouseholdUserListViewModel @Inject constructor(
    private val userUseCases: UserUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val householdId: String = savedStateHandle.get<String>("householdId")?: ""

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow: SharedFlow<UiEvent> = _eventFlow.asSharedFlow()

    private var getUsersJob: Job? = null

    init {
        getUsers()
    }

    private fun getUsers() {
        getUsersJob?.cancel()
        getUsersJob = userUseCases.getUsersFromHousehold(householdId)
            .onEach { userList ->
                _users.value = userList
            }
            .launchIn(viewModelScope)
    }

    fun onEvent(event: UserListEvent) {
        when (event) {
            // Nothing yet

            else -> {}
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
    }
}

