package com.example.rekammedisapp.helper

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// DEPRECATED

object DateHelper {
    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
        val date = Date()
        return dateFormat.format(date)
    }
}