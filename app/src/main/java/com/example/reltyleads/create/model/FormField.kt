package com.example.reltyleads.create.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType

sealed class InputType {
    data class Text(val textInputType: TextInputType) : InputType()
    data class Dropdown(val options: List<Pair<String, String>>) : InputType()
    data class Date(val dateFormat: String) : InputType()
    data class File(val fileName: String) : InputType() // You can extend this further as needed
}

enum class TextInputType {
    NAME, NUMBER, EMAIL, PHONE
}

data class FormField(
    val placeholder: String = "",
    val type: InputType,
    var text: String = "",
    val isVisible: Boolean = false,
    val isEnabled: Boolean = true,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val singleLine: Boolean = false,
    val maxLines: Int = 1,
    var leadingIcon: @Composable (() -> Unit)? = null,
    var trailingIcon: @Composable (() -> Unit)? = null,
    val keyboardType: KeyboardType = KeyboardType.Text,
    val imeAction: ImeAction = ImeAction.Done
)