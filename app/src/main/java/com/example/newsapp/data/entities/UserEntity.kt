package com.example.newsapp.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    val email: String,
    val name: String,
    val password: String,
    val phoneNumber: String
)
