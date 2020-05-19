package com.zhaldak.myapplication.datadase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ContactGroup::class, Contact::class], version = 2)

/**
 * DataBase class for the application
 */
 
abstract class AppDatabase : RoomDatabase() {
    abstract fun getGroupsDao(): ContactGroupsDao
    abstract fun getContactsDao(): ContactsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                //Create database
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}