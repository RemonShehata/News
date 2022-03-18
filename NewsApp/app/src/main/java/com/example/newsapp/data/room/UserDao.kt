package com.example.newsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.entities.User

@Dao
interface UserDao {

    //TODO test how the abort works.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User): Long

    @Query("SELECT EXISTS(SELECT * FROM User WHERE email = :email AND password = :password)")
    suspend fun login(email: String, password: String): Boolean

    @Query("UPDATE user SET password=:password WHERE email = :email")
    suspend fun updatePassword(email:String, password: String): Int
}