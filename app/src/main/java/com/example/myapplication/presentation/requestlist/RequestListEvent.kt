package com.example.myapplication.presentation.requestlist

sealed class RequestListEvent {
    data class ChangeSelectedTab(val newIndex: Int) : RequestListEvent()
    //data class FulfillRequest(val requestId: Long, val fulfilledByUserId: Long) : RequestListEvent()
}