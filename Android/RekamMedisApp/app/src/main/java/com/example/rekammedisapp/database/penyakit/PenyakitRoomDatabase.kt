package com.example.rekammedisapp.database.penyakit

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// DEPRECATED
@Database(entities = [Penyakit::class], version = 1)
abstract class PenyakitRoomDatabase : RoomDatabase() {
    abstract fun penyakitDao(): PenyakitDao

    companion object {
        @Volatile
        private var INSTANCE: PenyakitRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): PenyakitRoomDatabase {
            if (INSTANCE == null) {
                synchronized(PenyakitRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        PenyakitRoomDatabase::class.java, "penyakit_database"
                    ).build()
                }
            }
            return INSTANCE as PenyakitRoomDatabase
        }
    }
}
