package com.example.myapplication.data.util

import java.math.BigDecimal

fun BigDecimal?.toFirestoreString(): String? {
    return this?.toPlainString()
}


fun String?.toBigDecimalFromFirestore(): BigDecimal? {
    return this?.let { BigDecimal(it) }
}


