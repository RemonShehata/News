package com.example.newsapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.newsapp.data.entities.UserEntity

@Dao
interface UserDao {
    /**
     * According to this [link](https://commonsware.com/AndroidArch/pages/chap-roomconflict-001)
     * The conflict will happen when two entities have the same primary keys.
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(userEntity: UserEntity): Long

    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE email = :email AND password = :password)")
    suspend fun login(email: String, password: String): Boolean

    @Query("UPDATE user SET password=:password WHERE email = :email")
    suspend fun updatePassword(email: String, password: String): Int
}
