package com.example.businessscout.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Bussiness::class, BussinessNote::class, BussinessCollection::class, BussinessCollectionCrossRef::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bussinessDao(): BussinessDao
    abstract fun businessNoteDao(): BussinessNoteDao
    abstract fun businessCollectionDao(): BussinessCollectionDao
    abstract  fun bussinessCollectionCrossRefDao(): BussinessCollectionCrossRefDao


    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).
                fallbackToDestructiveMigration()
                    .
                        allowMainThreadQueries().
                build()
                INSTANCE = instance
                return instance
            }
        }
    }
}