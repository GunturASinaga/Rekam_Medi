package com.example.rekammedisapp.data.API


import com.example.rekammedisapp.data.request.AlergiRequestBody
import com.example.rekammedisapp.data.request.LoginRequest
import com.example.rekammedisapp.data.request.PenyakitRequestBody
import com.example.rekammedisapp.data.request.RegisterRequest
import com.example.rekammedisapp.data.response.AlergiResponse
import com.example.rekammedisapp.data.response.HomepageResponse
import com.example.rekammedisapp.data.response.LoginResponse
import com.example.rekammedisapp.data.response.ObatResponse
import com.example.rekammedisapp.data.response.ObatSpesifikResponse
import com.example.rekammedisapp.data.response.PenyakitEditResponse
import com.example.rekammedisapp.data.response.PenyakitLengkapResponse
import com.example.rekammedisapp.data.response.PenyakitResponse
import com.example.rekammedisapp.data.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

// merupakan endpoint yang digunakan auntuk mengakses APi beserta data yang dgiuankan untuk menampung data dari API

interface ApiService {
    @POST("auth/login")
    fun login(
        @Body request: LoginRequest
    ): Call<LoginResponse>

    @POST("auth/register")
    fun register(
        @Body request: RegisterRequest
    ): Call<RegisterResponse>

    @GET("/")
    fun getPenyakitLengkap(
        @Header("Authorization") authToken: String
    ): Call<HomepageResponse>

    @GET("/penyakit/id/{id}")
    fun getPenyakitTunggal(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String
    ): Call<PenyakitResponse>

    @GET("/penyakit/lengkap")
    fun getPenyakitObat(
        @Header("Authorization") authToken: String
    ): Call<PenyakitLengkapResponse>

    @POST("/alergi") // Adjust the endpoint URL
    fun tambahAlergi(
        @Header("Authorization") authToken: String,
        @Body requestBody: Map<String, String>
    ): Call<AlergiResponse>

    @PUT("/alergi/{id}")
    fun editAlergi(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
        @Body requestBody: Map<String, String>
    ): Call<AlergiResponse>

    @DELETE("/alergi/{id}")
    fun hapusAlergi(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
    ): Call<AlergiResponse>

    @DELETE("/penyakit/{id}")
    fun hapusPenyakit(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
    ): Call<PenyakitEditResponse>

    @POST("/penyakit/")
    fun tambahPenyakit(
        @Header("Authorization") authToken: String,
        @Body requestBody: PenyakitRequestBody
    ): Call<AlergiResponse>

    @PUT("/penyakit/{id}")
    fun updatePenyakit(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
        @Body requestBody: PenyakitRequestBody
    ): Call<PenyakitEditResponse>


    // OBAT
    @GET("/obat/{id}")
    fun getObat(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String
    ) : Call<ObatResponse>

    @POST("/obat/{id}")
    fun tambahObat(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
        @Body requestBody: Map<String, String>
    ) : Call<AlergiResponse>

    @DELETE("/obat/{id}")
    fun hapusObat(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
    ) : Call<PenyakitEditResponse>

    @PUT("/obat/{id}")
    fun editObat(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String,
        @Body requestBody: Map<String, String>
    ) : Call<AlergiResponse>

    @GET("/obat/spesifik/{id}")
    fun getObatSpesifik(
        @Path("id") id: Int,
        @Header("Authorization") authToken: String
    ) : Call<ObatSpesifikResponse>
}