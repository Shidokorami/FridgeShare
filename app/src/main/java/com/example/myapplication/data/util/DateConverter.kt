package com.example.myapplication.data.util

import java.time.LocalDate
import java.time.ZoneId

object DateConverter {
    fun date(year: Int, month: Int, day: Int): Long {
        return LocalDate.of(year, month, day)
            .atStartOfDay(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}