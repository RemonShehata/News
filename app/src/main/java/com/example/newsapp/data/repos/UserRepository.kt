package com.example.newsapp.data.repos

import com.example.newsapp.data.entities.UserEntity

interface UserRepository {

    suspend fun registerUser(userEntity: UserEntity): Boolean

    suspend fun login(email: String, password: String): Boolean

    suspend fun changePassword(email: String, password: String): Boolean
}
