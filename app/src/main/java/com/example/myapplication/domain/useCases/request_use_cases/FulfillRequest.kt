package com.example.myapplication.domain.useCases.request_use_cases

import com.example.myapplication.domain.model.Product
import com.example.myapplication.domain.model.ProductRequest
import com.example.myapplication.domain.useCases.product_use_cases.ProductUseCases

class FulfillRequest(
    private val addRequest: AddRequest,
    private val productUseCases: ProductUseCases
) {

    suspend operator fun invoke(request: ProductRequest, householdId: String){
        addRequest(householdId, request)
        val product = Product(
            id = "",
            householdId = householdId,
            name = request.name,
            quantity = request.quantity,
            unit = request.unit,
            buyerId = request.fulfilledBy,

        )
        productUseCases.addProduct(householdId, product)
    }
}
