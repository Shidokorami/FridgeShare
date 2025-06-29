package com.example.myapplication.presentation.singup

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.useCases.auth_use_cases.AuthUseCases
import com.example.myapplication.presentation.loginscreen.LoginState
import com.example.myapplication.presentation.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


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
                _uiState.value = _uiState.value.copy(passwordConfirmation = event.password, errorMessage = null)
            }
            SignUpEvent.SignUpClicked -> {

            }
            SignUpEvent.ErrorDismissed -> {
                _uiState.value = _uiState.value.copy(errorMessage = null)
            }
        }
    }

}