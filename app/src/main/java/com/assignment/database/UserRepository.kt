package com.assignment.database

class UserRepository(private val userDao: UserDao) {

    suspend fun insert(user: User){
        userDao.insertUser(user)
    }

    suspend fun checkEmail(email:String):User{
        return userDao.checkEmail(email)
    }

    suspend fun checkPassword(password:String):User{
        return userDao.checkPassword(password)
    }
}