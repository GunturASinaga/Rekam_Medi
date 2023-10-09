package com.example.rekammedisapp.model

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// merupakan ViewModel utama yang bertujuan untuk mendistribusikan viewmodel agar tidak terjadi error

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PenyakitViewModel::class.java)) {
            return PenyakitViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(AlergiViewModel::class.java)) {
            return AlergiViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(PenyakitApiViewModel::class.java)) {
            return PenyakitApiViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(AlergiAPIViewModel::class.java)) {
            return AlergiAPIViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(ObatViewModel::class.java)) {
            return ObatViewModel(mApplication) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}