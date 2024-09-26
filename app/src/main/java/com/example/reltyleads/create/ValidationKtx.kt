package com.example.reltyleads.create

import android.util.Log
import android.util.Patterns

fun isNumber(value: String): Boolean {
    return value.isNotBlank() && Regex("^\\d+\$").matches(value)
}

fun isEmailValid(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun isNameValid(value: String): Boolean {
    return value.isNotEmpty() && Regex("^[A-Za-z]+(?: [A-Za-z]+)* ?\$").matches(value)
}

fun isPhoneValid(value: String): Boolean {
    return value.isNotBlank() && Regex("^\\d{10}\$").matches(value)
}

fun isFloorNumberValid(value: String): Boolean {
    return value.isNotBlank() && Regex("^\\d{1,3}\$").matches(value)
}

fun isUnitSizeValid(value: String): Boolean {
    return value.isNotBlank() && Regex("^\\d{1,5}\$").matches(value)
}