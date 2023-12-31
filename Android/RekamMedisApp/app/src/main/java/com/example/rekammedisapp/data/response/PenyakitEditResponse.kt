package com.example.rekammedisapp.data.response

import com.google.gson.annotations.SerializedName

data class PenyakitEditResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null
)
