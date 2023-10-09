package com.example.rekammedisapp.data.request

data class PenyakitRequestBody(
    val nama: String,
    val gejala: String,
    val tanggal_mulai: String,
    val tindakan_medis: String,
    val tanggal_selesai: String? = null,
    val hasil: String? = null
)