package com.assignment.ui.login

import android.app.Application
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.database.User
import com.assignment.database.UserRepository
import com.assignment.database.UserRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var isLogin = true
    var email = ""
    var password = ""


    var observer = MutableLiveData<String>()
    var observerSuccess = MutableLiveData<Boolean>()
    var observerData = MutableLiveData<Boolean>()

    private lateinit var userRepository: UserRepository

    init {
        val userDao = UserRoomDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun insert(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.insert(user)
    }

    suspend fun checkEmail(email: String): User {
        return viewModelScope.async(Dispatchers.IO) {
            userRepository.checkEmail(email)
        }.await()
    }

    suspend fun checkPassword(password: String): User {
        return viewModelScope.async(Dispatchers.IO) {
            userRepository.checkPassword(password)
        }.await()
    }

    fun loginButton() {
        if (TextUtils.isEmpty(email)) {
            observer.postValue("Email is empty")
        } else if (TextUtils.isEmpty(password)) {
            observer.postValue("Password is empty")
        } else {
            GlobalScope.launch {
                val isEmailExist = checkEmail(email)
                if (isEmailExist == null) {
                    observer.postValue("Email does not exist")
                } else {
                    val isPasswordMatch = checkPassword(password)
                    if (isPasswordMatch == null) {
                        observer.postValue("Password does not match")
                    } else {
                        observerSuccess.postValue(true)
                    }
                }
            }

        }
    }

    fun signUpButton() {
        if (TextUtils.isEmpty(email)) {
            observer.postValue("Email is empty")
        } else if (TextUtils.isEmpty(password)) {
            observer.postValue("Password is empty")
        } else {
            GlobalScope.launch {
                val isEmailExist = checkEmail(email)
                if (isEmailExist != null) {
                    observer.postValue("Email Already exists")
                } else {
                    val user = User(0, email, password)
                    insert(user)
                    observerSuccess.postValue(true)
                }
            }


        }
    }

    fun navigate() {
        println("LoginViewModel.navigate")
        observerData.postValue(true)
    }

}
