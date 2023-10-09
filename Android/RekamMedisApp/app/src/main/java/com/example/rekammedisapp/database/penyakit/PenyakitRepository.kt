package com.example.rekammedisapp.database.penyakit

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
// DEPRECATED
class PenyakitRepository(application: Application) {
    private val mPenyakitsDao: PenyakitDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = PenyakitRoomDatabase.getDatabase(application)
        mPenyakitsDao = db.penyakitDao()
    }

    fun getPenyakitNotes(): LiveData<List<Penyakit>> = mPenyakitsDao.getAllNotes()

    fun insert(note: Penyakit) {
        executorService.execute { mPenyakitsDao.insert(note) }
    }

    fun delete(note: Penyakit) {
        executorService.execute { mPenyakitsDao.delete(note) }
    }

    fun update(note: Penyakit) {
        executorService.execute { mPenyakitsDao.update(note) }
    }
}