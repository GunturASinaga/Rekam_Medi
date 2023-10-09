package com.example.rekammedisapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.temantani.db.UserAuth
import com.example.rekammedisapp.data.API.ApiConfig
import com.example.rekammedisapp.data.request.LoginRequest
import com.example.rekammedisapp.data.response.LoginResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Viewmodel untuk mengatur aliran data yang dibutuhakan untuk login dan juga berkomunikasi dengan database

class LoginViewModel(application: Application) : ViewModel() {

    private val _loginResponse = MutableLiveData<LoginResponse>()
    val loginResponse : LiveData<LoginResponse> = _loginResponse

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    // memulai proses login
     fun login(username : String, password : String) {
//        val loginRequest = LoginRequest(username = "Gunturs", password = "Passw0rd")
        val loginRequest = LoginRequest(username = username, password = password)

        val client = ApiConfig.getApiService().login(loginRequest)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here

                        _loginResponse.value = response.body()
                    }
                } else {
                    Log.e("LoginResponse", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginResponse", "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}