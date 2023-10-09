package com.example.rekammedisapp.database.penyakit

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
// DEPRECATED
@Entity
@Parcelize
data class Penyakit (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "nama")
    var nama: String? = null,

    @ColumnInfo(name = "gejala")
    var gejala: String? = null,

    @ColumnInfo(name = "tindakan medis")
    var tindakanMedis: String? = null,

    @ColumnInfo(name = "hasil")
    var hasil: String? = null,

    @ColumnInfo(name = "awal")
    var awal: String? = null,

    @ColumnInfo(name = "akhir")
    var akhir: String? = null,

    ): Parcelable