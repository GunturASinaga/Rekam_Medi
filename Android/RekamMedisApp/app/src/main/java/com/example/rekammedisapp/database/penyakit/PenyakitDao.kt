package com.example.rekammedisapp.database.penyakit

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
// DEPRECATED
@Dao
interface PenyakitDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note: Penyakit)

    @Update
    fun update(note: Penyakit)

    @Delete
    fun delete(note: Penyakit)

    @Query("SELECT * from penyakit ORDER BY id ASC")
    fun getAllNotes(): LiveData<List<Penyakit>>

}