package com.example.newsapp.utils

import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

fun TextInputEditText.getTrimmedText(): String {
    return this.editableText.toString().trim()
}

fun String.isValidEmailFormat(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isValidPhoneNumberFormat(): Boolean {
    return this.matches(Regex(PHONE_REGEX))
}

fun String.isValidPasswordFormat(): Boolean {
    return this.length >= MIN_PASSWORD_LENGTH
}
