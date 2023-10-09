package com.example.rekammedisapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.dicoding.temantani.db.UserAuth
import com.dicoding.temantani.db.UserPreference
import com.example.rekammedisapp.R
import com.example.rekammedisapp.data.API.ApiConfig
import com.example.rekammedisapp.data.request.LoginRequest
import com.example.rekammedisapp.data.response.LoginResponse
import com.example.rekammedisapp.databinding.ActivityLoginBinding
import com.example.rekammedisapp.model.LoginViewModel
import com.example.rekammedisapp.model.ViewModelFactory
import com.example.rekammedisapp.ui.home.HomeActivity
import com.example.rekammedisapp.ui.register.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log

// tampilan untuk mengatur proses login

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel : LoginViewModel

    private lateinit var userAuth : UserAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loginViewModel = obtainViewModel(this@LoginActivity)

        // untuk berpindah ke menu register
        binding.register.setOnClickListener{
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        // menentukan aksi setelah login
        loginViewModel.loginResponse.observe(this){response ->
            if(response.status == "success"){
                val userName = response.username
                val userToken = response.token
                if (userName != null) {
                    if (userToken != null) {
                        saveUserAuth(userName, userToken)
                    }
                }
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
            }
        }

        // menentukan aksi setelah menekan login button, yaitu mengirimkan dat  ke loginViewmodel
        binding.loginBtn.setOnClickListener{
            val edUsernameText = binding?.emailTelpEd?.text.toString()
            val edPasswordText = binding?.passwordEd?.text.toString()
            loginViewModel.login(edUsernameText, edPasswordText)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity) : LoginViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(LoginViewModel::class.java)
    }

    private fun saveUserAuth(user : UserAuth){
        val userPreference = UserPreference(this)
        userPreference.setUserLogin(user)
    }

    private fun saveUserAuth(name : String, token : String){
        val userPreference = UserPreference(this)
        val userDataResponse = UserAuth()

        userDataResponse.name = name
        userDataResponse.token = token

        userAuth = userDataResponse
        userPreference.setUserLogin(userAuth)
        Log.e("LoginActivity", "Berhasil menambahkan")
    }

}