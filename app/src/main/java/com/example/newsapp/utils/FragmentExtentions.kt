package com.example.newsapp.utils

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showMessage(message: String) {
    Snackbar.make(this.requireView(), message, Snackbar.LENGTH_LONG)
        .show()
}