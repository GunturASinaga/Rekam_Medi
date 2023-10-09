package com.example.rekammedisapp.database.alergi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
// DEPRECATED
@Database(entities = [Alergi::class], version = 1)
abstract class AlergiRoomDatabase : RoomDatabase() {
    abstract fun alergiDao(): AlergiDao

    companion object {
        @Volatile
        private var INSTANCE: AlergiRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): AlergiRoomDatabase {
            if (INSTANCE == null) {
                synchronized(AlergiRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AlergiRoomDatabase::class.java, "alergi_database"
                    ).build()
                }
            }
            return INSTANCE as AlergiRoomDatabase
        }
    }
}
