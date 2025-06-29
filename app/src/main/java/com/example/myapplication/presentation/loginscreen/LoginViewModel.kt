package com.example.myapplication.presentation.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.useCases.auth_use_cases.AuthUseCases
import com.example.myapplication.domain.util.AuthResult
import com.example.myapplication.presentation.navigation.HouseholdListScreen
import com.example.myapplication.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginState())
    val uiState: StateFlow<LoginState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email, errorMessage = null)
            }
            is LoginEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password, errorMessage = null)
            }
            LoginEvent.SignInClicked -> {
                signIn()
            }
            LoginEvent.ErrorDismissed -> {
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }
        }
    }

    private fun signIn() {
        val currentUiState = _uiState.value
        val currentEmail = currentUiState.email
        val currentPassword = currentUiState.password

        if (currentEmail.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Email cannot be empty.")
            return
        }
        if (currentPassword.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Password cannot be empty.")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

        viewModelScope.launch {
            when (val result = authUseCases.signIn(currentEmail, currentPassword)) {
                is AuthResult.Success -> {
                    _eventFlow.emit(UiEvent.Navigate(HouseholdListScreen))
                    _uiState.value = _uiState.value.copy(isLoading = false, errorMessage = null)
                }
                is AuthResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                    _eventFlow.emit(UiEvent.ShowSnackbar(result.message))
                }
                AuthResult.Loading -> Unit
            }
        }
    }
}