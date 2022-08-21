package com.example.developerslifekotlin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseGif::class], version = 1, exportSchema = false)
abstract class DatabaseInit : RoomDatabase() {

    abstract val gifsDatabaseDao: GifsDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: DatabaseInit? = null

        fun getDatabase(context: Context): DatabaseInit {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DatabaseInit::class.java,
                        "gifs_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
