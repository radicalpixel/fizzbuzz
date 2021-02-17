package com.waxym.utils.extension

import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputLayout
import com.waxym.R


fun TextInputLayout.asString(): String? = editText?.text?.toString()

fun TextInputLayout.asInt(): Int? = editText?.text?.toString()?.toIntOrNull()

fun TextInputLayout.requireNotBlank(@StringRes error: Int = R.string.form_error_mandatory_field): TextInputLayout? {
    return require(error) { !it.editText?.text.isNullOrBlank() }
}

fun TextInputLayout.requireInteger(@StringRes error: Int = R.string.form_error_type_number): TextInputLayout? {
    return require(error) { it.asInt() != null }
}

fun TextInputLayout.requirePositiveInteger(@StringRes error: Int = R.string.form_error_type_positive_integer): TextInputLayout? {
    return require(error) { it.asInt() ?: 0 > 0 }
}

fun TextInputLayout.require(@StringRes message: Int, predicate: (TextInputLayout) -> Boolean): TextInputLayout? {
    return if (predicate(this)) {
        error = null
        this
    } else {
        error = context.getString(message)
        null
    }
}