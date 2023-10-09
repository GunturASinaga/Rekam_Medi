package com.example.rekammedisapp.data.request

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @field:SerializedName("username")
    val username: String,

    @field:SerializedName("birth_date")
    val birthDate: String, // Format: YYYY-MM-DD

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("kontak")
    val kontak: String,

    @field:SerializedName("password")
    val password: String
)