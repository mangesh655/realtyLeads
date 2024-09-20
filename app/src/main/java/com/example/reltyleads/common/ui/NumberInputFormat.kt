package com.example.reltyleads.common.ui

import java.text.NumberFormat
import java.util.Locale

fun formatNumberWithComma(input: String): String {
    return if (input.isNotEmpty()) {
        val number = input.replace(",", "").toLongOrNull()
        number?.let {
            NumberFormat.getNumberInstance(Locale.US).format(it)
        } ?: input // Return input if it's not a valid number
    } else {
        input
    }
}