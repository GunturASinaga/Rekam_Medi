package com.example.rekammedisapp.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rekammedisapp.database.penyakit.Penyakit
import com.example.rekammedisapp.database.penyakit.PenyakitRepository

// DEPRECATED

class PenyakitViewModel(application: Application) : ViewModel() {

    private val mPenyakitRepository: PenyakitRepository = PenyakitRepository(application)

    fun getAllPenyakit(): LiveData<List<Penyakit>> = mPenyakitRepository.getPenyakitNotes()

    fun insert(penyakit: Penyakit) {
        mPenyakitRepository.insert(penyakit)
    }

    fun update(penyakit: Penyakit) {
        mPenyakitRepository.update(penyakit)
    }

    fun delete(penyakit: Penyakit) {
        mPenyakitRepository.delete(penyakit)
    }

}