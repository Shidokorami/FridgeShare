package com.example.myapplication.domain.useCases.request_use_cases

data class RequestUseCases(
    val getRequestsByFulfill: GetRequestsByFulfill,
    val getRequest: GetRequest,
    val addRequest: AddRequest,
    val deleteRequest: DeleteRequest
)