package com.assignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Query("Select * from user where email = :email")
    fun checkEmail(email:String) : User

    @Query("Select * from user where password = :password")
    fun checkPassword(password:String) : User

    @Insert
    fun insertUser(user:User)
}