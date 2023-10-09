package com.example.rekammedisapp.model

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.rekammedisapp.database.alergi.Alergi
import com.example.rekammedisapp.database.alergi.AlergiRepository
import com.example.rekammedisapp.database.penyakit.Penyakit
import com.example.rekammedisapp.database.penyakit.PenyakitRepository

// depreccated

class AlergiViewModel(application: Application) : ViewModel() {

    private val mAlergiRepository: AlergiRepository = AlergiRepository(application)

    fun insert(penyakit: Alergi) {
        mAlergiRepository.insert(penyakit)
    }

    fun update(penyakit: Alergi) {
        mAlergiRepository.update(penyakit)
    }

    fun delete(penyakit: Alergi) {
        mAlergiRepository.delete(penyakit)
    }

    fun getAllAlergi(): LiveData<List<Alergi>> = mAlergiRepository.getAlergiNotes()
}