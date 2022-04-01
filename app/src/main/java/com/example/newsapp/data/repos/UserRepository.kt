package com.example.newsapp.data.repos

import com.example.newsapp.data.entities.User

interface UserRepository {

    suspend fun registerUser(user: User): Boolean

    suspend fun login(email: String, password: String): Boolean

    suspend fun changePassword(email: String, password: String): Boolean
}
