package com.example.news.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {
    fun formatToDisplay(dateMillis: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(Date(dateMillis))
    }
}
