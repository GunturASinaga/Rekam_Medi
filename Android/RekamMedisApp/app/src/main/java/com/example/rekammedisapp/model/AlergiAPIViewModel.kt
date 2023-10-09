package com.example.rekammedisapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rekammedisapp.data.API.ApiConfig
import com.example.rekammedisapp.data.request.AlergiRequestBody
import com.example.rekammedisapp.data.response.AlergiResponse
import com.example.rekammedisapp.data.response.PenyakitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ViewModel untuk aksi pada data alergi

class AlergiAPIViewModel  (application: Application) : ViewModel(){

    private val _alergiResponse = MutableLiveData<AlergiResponse>()
    val alergiResponse : LiveData<AlergiResponse> = _alergiResponse

    // fungsi untuk menambah alergi
    fun tambahAlergi(authToken : String, nama : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")
        val requestBody = mapOf("nama" to nama)

        val client = ApiConfig.getApiService().tambahAlergi(authToken, requestBody)
        client.enqueue(object : Callback<AlergiResponse> {
            override fun onResponse(
                call: Call<AlergiResponse>,
                response: Response<AlergiResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _alergiResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<AlergiResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }


    // fungsi untuk mengedit alergi
    fun editAlergi(id : Int, authToken : String, nama : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")
        val requestBody = mapOf("nama" to nama)

        val client = ApiConfig.getApiService().editAlergi(id, authToken, requestBody)
        client.enqueue(object : Callback<AlergiResponse> {
            override fun onResponse(
                call: Call<AlergiResponse>,
                response: Response<AlergiResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _alergiResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<AlergiResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }

    // fungsi untuk menghapus alergi
    fun hapusAlergi(id : Int, authToken : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")


        val client = ApiConfig.getApiService().hapusAlergi(id, authToken)
        client.enqueue(object : Callback<AlergiResponse> {
            override fun onResponse(
                call: Call<AlergiResponse>,
                response: Response<AlergiResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _alergiResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<AlergiResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }
}