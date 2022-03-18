package com.example.newsapp.features.auth

import androidx.lifecycle.ViewModel
import com.example.newsapp.data.repos.UserRepository

class AuthViewModel(
    private val userRepository: UserRepository
) : ViewModel()