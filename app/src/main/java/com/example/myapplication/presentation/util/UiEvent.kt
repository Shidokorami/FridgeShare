package com.example.myapplication.presentation.util

sealed class UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent()
    data class Navigate(val route: Any) : UiEvent()
    data class NavigateAndPopUp(val route: Any, val popUpTo: Any, val inclusive: Boolean): UiEvent()
}