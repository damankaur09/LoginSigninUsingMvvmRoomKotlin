package com.assignment.ui.splash

import android.R.attr.x
import android.app.Application
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.R


class SplashViewModel(application: Application) : AndroidViewModel(application) {

    var observer = MutableLiveData<String>()
   init{
        Thread(Runnable {
            try {
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            observer.postValue("fragment")
        }).start()
    }
}
