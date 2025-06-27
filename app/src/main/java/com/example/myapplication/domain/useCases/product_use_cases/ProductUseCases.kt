package com.example.myapplication.domain.useCases.product_use_cases

data class ProductUseCases(
    val getProductsByHousehold: GetProductsFromHousehold,
    val getProduct: GetProduct,
    val addProduct: AddProduct
)