package com.grayfien.testugd1.package_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [Pasien::class],
    version = 1
)

abstract class PasienDB : RoomDatabase(){

    abstract fun pasienDao() : PasienDAO

    companion object {
        @Volatile private var instance : PasienDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                PasienDB::class.java,
                "Pasien.db"
            ).build()
    }
}