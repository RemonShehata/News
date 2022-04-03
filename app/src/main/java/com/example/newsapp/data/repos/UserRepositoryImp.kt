package com.example.newsapp.data.repos

import android.database.sqlite.SQLiteConstraintException
import com.example.newsapp.data.entities.UserEntity
import com.example.newsapp.data.room.UserDao

class UserRepositoryImp(private val userDao: UserDao) : UserRepository {

    @Suppress("SwallowedException")
    override suspend fun registerUser(userEntity: UserEntity): Boolean {
        return try {
            userDao.insertUser(userEntity)
            true
        } catch (exception: SQLiteConstraintException) {
            false
        }
    }

    override suspend fun login(email: String, password: String): Boolean {
        return userDao.login(email, password)
    }

    override suspend fun changePassword(email: String, password: String): Boolean {
        return userDao.updatePassword(email, password) > 0
    }
}
