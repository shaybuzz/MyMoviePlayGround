package com.tut.moviemvvm.datasource.local.entity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tut.mymovieplayground.datasource.local.ConvertersHelper
import com.tut.mymovieplayground.datasource.local.MovieDao

@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(ConvertersHelper::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            var instance = INSTANCE
            if (instance != null) return instance
            synchronized(this) {
                instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        "movies_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return INSTANCE!!
                } else {
                    return INSTANCE!!
                }
            }
        }
    }
}