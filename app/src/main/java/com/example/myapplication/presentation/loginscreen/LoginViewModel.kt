package com.example.myapplication.presentation.loginscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.useCases.auth_use_cases.AuthUseCases
import com.example.myapplication.domain.util.AuthResult
import com.example.myapplication.presentation.navigation.HouseholdListScreen
import com.example.myapplication.presentation.navigation.LoginScreen
import com.example.myapplication.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Email cannot be empty.")
            return
        }
        if (password.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Password cannot be empty.")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            when (val result = authUseCases.signIn(email, password)) {
                is AuthResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.NavigateAndPopUp(
                            route = HouseholdListScreen,
                            popUpTo = LoginScreen,
                            inclusive = true
                        )
                    )
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
