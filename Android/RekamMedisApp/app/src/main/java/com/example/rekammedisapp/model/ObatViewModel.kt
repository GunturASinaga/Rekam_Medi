package com.example.rekammedisapp.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rekammedisapp.data.API.ApiConfig
import com.example.rekammedisapp.data.response.AlergiResponse
import com.example.rekammedisapp.data.response.ObatResponse
import com.example.rekammedisapp.data.response.ObatSpesifikResponse
import com.example.rekammedisapp.data.response.PenyakitEditResponse
import com.example.rekammedisapp.data.response.PenyakitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// mengatur proses aliran data untuk obat

class ObatViewModel (application: Application) : ViewModel() {
    private val _obatResponse = MutableLiveData<ObatResponse>()
    val obatResponse : LiveData<ObatResponse> = _obatResponse

    private val _obat = MutableLiveData<ObatSpesifikResponse>()
    val obat : LiveData<ObatSpesifikResponse> = _obat

    private val _tambahResponse = MutableLiveData<AlergiResponse>()
    val tambahResponse : LiveData<AlergiResponse> = _tambahResponse


    // mendapatkan data obat secara spesifik
    fun getObatTunggal(id : Int, authToken : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")

        val client = ApiConfig.getApiService().getObat(id,authToken)
        client.enqueue(object : Callback<ObatResponse> {
            override fun onResponse(
                call: Call<ObatResponse>,
                response: Response<ObatResponse>
            ) {
                Log.d("cobaPenyakitrequest", call.request().toString()) // Log the request
                Log.d("cobaPenyakitresponse", response.code().toString()) // Log the response code
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _obatResponse.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<ObatResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }

    fun getObatSpesifik(id : Int, authToken : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")

        val client = ApiConfig.getApiService().getObatSpesifik(id,authToken)
        client.enqueue(object : Callback<ObatSpesifikResponse> {
            override fun onResponse(
                call: Call<ObatSpesifikResponse>,
                response: Response<ObatSpesifikResponse>
            ) {
                Log.d("cobaPenyakitrequest", call.request().toString()) // Log the request
                Log.d("cobaPenyakitresponse", response.code().toString()) // Log the response code
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        // Handle successful login response here
                        if(responseBody.status == "success"){
                            _obat.value = response.body()
                            Log.e("cobaPenyakit", "${response.body().toString()}")
                        } else{
                            Log.e("cobaPenyakit", "Gagal")
                        }
                    }
                } else {
                    Log.e("cobaPenyakit", "onFailure: ${response.body().toString()}")
                }
            }
            override fun onFailure(call: Call<ObatSpesifikResponse>, t: Throwable) {
                Log.e("cobaPenyakit", "onFailure: ${t.message}")
            }
        })
    }

    // menambahkan obat ke dalam databas
    fun tambahObat(id: Int, authToken : String, nama : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")
        val requestBody = mapOf("nama_obat" to nama)

        val client = ApiConfig.getApiService().tambahObat(id, authToken, requestBody)
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
                            _tambahResponse.value = response.body()
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


    // melakukan pengeditan data obat
    fun editObat(id: Int, authToken : String, nama : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")
        val requestBody = mapOf("nama_obat" to nama)

        val client = ApiConfig.getApiService().editObat(id, authToken, requestBody)
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
                            _tambahResponse.value = response.body()
                            Log.e("cobaPenyakit", "Edit ${response.body().toString()}")
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

    // melakukan pengedit data obat
    fun hapusObat(id: Int, authToken : String) {
        Log.e("cobaPenyakit", "masuk ke fungsi ${authToken}")

        val client = ApiConfig.getApiService().hapusObat(id, authToken)
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
                            Log.e("cobaPenyakit", "hapus ${response.body().toString()}")
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