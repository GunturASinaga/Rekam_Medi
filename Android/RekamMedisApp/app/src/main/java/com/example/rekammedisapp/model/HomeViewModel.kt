package com.example.rekammedisapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rekammedisapp.data.API.ApiConfig
import com.example.rekammedisapp.data.request.LoginRequest
import com.example.rekammedisapp.data.response.HomepageResponse
import com.example.rekammedisapp.data.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Viewmodel yang bertujuan untuk menampilkan dan mengirim data ke homepage

class HomeViewModel (application: Application) : ViewModel(){
    private val _homePageResponse = MutableLiveData<HomepageResponse>()
    val homepageResponse : LiveData<HomepageResponse> = _homePageResponse

    // mengirimkan data yang dibutuhkan di homepage, seperti
    fun getHomeData(authToken : String) {
//        val loginRequest = LoginRequest(username = "Gunturs", password = "Passw0rd")

        val client = ApiConfig.getApiService().getPenyakitLengkap(authToken)
        client.enqueue(object : Callback<HomepageResponse> {
            override fun onResponse(
                call: Call<HomepageResponse>,
                response: Response<HomepageResponse>
            ) {

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _homePageResponse.value = response.body()
                        } else{
                        }
                    }
                } else {
                }
            }
            override fun onFailure(call: Call<HomepageResponse>, t: Throwable) {
            }
        })
    }
    companion object {
        private const val TAG = "LoginViewModel"
    }
}