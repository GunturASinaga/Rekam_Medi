package com.example.rekammedisapp.database.alergi

import android.app.Application
import androidx.lifecycle.LiveData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
// DEPRECATED
class AlergiRepository(application: Application) {
    private val mAlergiDao: AlergiDao

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = AlergiRoomDatabase.getDatabase(application)
        mAlergiDao = db.alergiDao()
    }

    fun getAlergiNotes(): LiveData<List<Alergi>> = mAlergiDao.getAllAlergis()

    fun insert(alergi: Alergi) {
        executorService.execute { mAlergiDao.insert(alergi) }
    }

    fun delete(alergi: Alergi) {
        executorService.execute { mAlergiDao.delete(alergi) }
    }

    fun update(alergi: Alergi) {
        executorService.execute { mAlergiDao.update(alergi) }
    }
}