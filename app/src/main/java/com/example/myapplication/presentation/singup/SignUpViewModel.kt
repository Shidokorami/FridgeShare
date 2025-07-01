package com.example.myapplication.presentation.singup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.useCases.auth_use_cases.AuthUseCases
import com.example.myapplication.domain.util.AuthResult
import com.example.myapplication.presentation.loginscreen.LoginState
import com.example.myapplication.presentation.navigation.HouseholdListScreen
import com.example.myapplication.presentation.navigation.LoginScreen
import com.example.myapplication.presentation.navigation.SignUpScreen
import com.example.myapplication.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
): ViewModel() {

    private val _uiState = MutableStateFlow(SignUpState())
    val uiState: StateFlow<SignUpState> = _uiState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event: SignUpEvent){
        when(event){
            is SignUpEvent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = event.email, errorMessage = null)
            }
            is SignUpEvent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = event.password, errorMessage = null)
            }
            is SignUpEvent.PasswordConfirmationChanged -> {
                _uiState.value = _uiState.value.copy(passwordConfirmation = event.confirmation, errorMessage = null)
            }
            SignUpEvent.SignUpClicked -> {
                signUp()
            }
            SignUpEvent.ErrorDismissed -> {
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }
        }
    }


    private fun signUp() {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val passConfirmation = _uiState.value.passwordConfirmation

        if (email.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Email cannot be empty.")
            return
        }
        if (password.isBlank()) {
            _uiState.value = _uiState.value.copy(errorMessage = "Password cannot be empty.")
            return
        }

        if (password != passConfirmation){
            _uiState.value = _uiState.value.copy(errorMessage = "Passwords do not match")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true)

        viewModelScope.launch {
            when (val result = authUseCases.signUp(email, password, passConfirmation)) {
                is AuthResult.Success -> {
                    _uiState.value = _uiState.value.copy(isLoading = false)
                    _eventFlow.emit(
                        UiEvent.NavigateAndPopUp(
                            route = LoginScreen,
                            popUpTo = SignUpScreen,
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