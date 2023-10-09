package com.example.rekammedisapp.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ObatSpesifikResponse(

	@field:SerializedName("data")
	val data: List<DataItemObatSpesifik?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)


@Parcelize
data class DataItemObatSpesifik(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("penyakit_id")
	val penyakitId: Int? = null,

	@field:SerializedName("nama_obat")
	val namaObat: String? = null
) : Parcelable
