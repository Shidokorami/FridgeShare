package com.example.myapplication.presentation.householdlist

sealed class HouseholdListEvent {
    data class ClickedCreateButton(val value: String): HouseholdListEvent()
}