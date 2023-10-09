package com.dicoding.temantani.db

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
// DEPRECATED
@Parcelize
data class UserAuth(
    var name : String ?= null,
    var token : String ?= null
) : Parcelable
