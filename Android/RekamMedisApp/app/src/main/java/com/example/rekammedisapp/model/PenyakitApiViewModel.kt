package com.example.rekammedisapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rekammedisapp.data.API.ApiConfig
import com.example.rekammedisapp.data.request.PenyakitRequestBody
import com.example.rekammedisapp.data.response.AlergiResponse
import com.example.rekammedisapp.data.response.HomepageResponse
import com.example.rekammedisapp.data.response.PenyakitEditResponse
import com.example.rekammedisapp.data.response.PenyakitLengkapResponse
import com.example.rekammedisapp.data.response.PenyakitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Viewmodel yang bertujuan untuk mengolah data penyakit, mengirim data, menambah, edit, dan hapus

class PenyakitApiViewModel (application: Application) : ViewModel(){
    private val _penyakitResponse = MutableLiveData<PenyakitResponse>()
    val penyakitResponse : LiveData<PenyakitResponse> = _penyakitResponse

    private val _updateResponse = MutableLiveData<PenyakitEditResponse>()
    val updateResponse : LiveData<PenyakitEditResponse> = _updateResponse

    private val _alergiResponse = MutableLiveData<AlergiResponse>()
    val alergiResponse : LiveData<AlergiResponse> = _alergiResponse

    private val _penyakitObatResponse = MutableLiveData<PenyakitLengkapResponse>()
    val penyakitObatResponse : LiveData<PenyakitLengkapResponse> = _penyakitObatResponse

    fun getPenyakitObat(authToken : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")

        val client = ApiConfig.getApiService().getPenyakitObat(authToken)
        client.enqueue(object : Callback<PenyakitLengkapResponse> {
            override fun onResponse(
                call: Call<PenyakitLengkapResponse>,
                response: Response<PenyakitLengkapResponse>
            ) {
                Log.d("cobaPenyakitrequest", call.request().toString()) // Log the request
                Log.d("cobaPenyakitresponse", response.code().toString()) // Log the response code
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if (responseBody.status == "success") {
                            _penyakitObatResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else {
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }

            override fun onFailure(call: Call<PenyakitLengkapResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }

    fun getPenyakitTunggal(id : Int, authToken : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")

        val client = ApiConfig.getApiService().getPenyakitTunggal(id,authToken)
        client.enqueue(object : Callback<PenyakitResponse> {
            override fun onResponse(
                call: Call<PenyakitResponse>,
                response: Response<PenyakitResponse>
            ) {
                Log.d("cobaPenyakitrequest", call.request().toString()) // Log the request
                Log.d("cobaPenyakitresponse", response.code().toString()) // Log the response code
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _penyakitResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<PenyakitResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }

    fun tambahPenyakit(authToken : String, sakit : PenyakitRequestBody) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")

        val client = ApiConfig.getApiService().tambahPenyakit(authToken, sakit)
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
                            Log.e("cobaPenyakit", " ini adalah edit${response.body().toString()}")
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

    fun editPenyakit(authToken : String, sakit : PenyakitRequestBody, id : Int) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")

        val client = ApiConfig.getApiService().updatePenyakit(id, authToken, sakit)
        client.enqueue(object : Callback<PenyakitEditResponse> {
            override fun onResponse(
                call: Call<PenyakitEditResponse>,
                response: Response<PenyakitEditResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _updateResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<PenyakitEditResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }

    fun hapusPenyakit(id : Int, authToken : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi hapus ${authToken}")

        val client = ApiConfig.getApiService().hapusPenyakit(id, authToken)
        client.enqueue(object : Callback<PenyakitEditResponse> {
            override fun onResponse(
                call: Call<PenyakitEditResponse>,
                response: Response<PenyakitEditResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _updateResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<PenyakitEditResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }
}