package com.example.rekammedisapp.data.response

import com.google.gson.annotations.SerializedName

data class PenyakitLengkapResponse(

	@field:SerializedName("data")
	val data: List<DataItemPenyakit?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

data class ObatItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("penyakit_id")
	val penyakitId: Int? = null,

	@field:SerializedName("nama_obat")
	val namaObat: String? = null
)

data class DataItemPenyakit(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("tanggal_selesai")
	val tanggalSelesai: String? = null,

	@field:SerializedName("obat")
	val obat: List<ObatItem?>? = null,

	@field:SerializedName("tanggal_mulai")
	val tanggalMulai: String? = null,

	@field:SerializedName("tindakan_medis")
	val tindakanMedis: String? = null,

	@field:SerializedName("hasil")
	val hasil: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("gejala")
	val gejala: String? = null
)
