package com.example.rekammedisapp.database.alergi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
// DEPRECATED
@Dao
interface AlergiDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(alergi: Alergi)

    @Update
    fun update(alergi: Alergi)

    @Delete
    fun delete(alergi: Alergi)

    @Query("SELECT * from alergi ORDER BY id ASC")
    fun getAllAlergis(): LiveData<List<Alergi>>
}