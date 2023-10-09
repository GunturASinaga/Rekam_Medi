package com.example.rekammedisapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class HomepageResponse(

	@field:SerializedName("penyakit")
	val penyakit: List<PenyakitItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("alergi")
	val alergi: List<AlergiItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)

@Parcelize
data class PenyakitItem(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("tanggal_selesai")
	val tanggalSelesai: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

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
) : Parcelable

@Parcelize
data class AlergiItem(
	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
