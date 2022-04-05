package com.max.natifeapptwo.data.room

import android.content.Context
import androidx.room.Room

class DatabaseSingleton private constructor() {

    private lateinit var applicationContext: Context

    val database: UserDatabase by lazy {
        Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "databaseDb"
        ).build()
    }

    fun init(context: Context) {
        applicationContext = context
    }

    companion object{

        @Volatile
        private var INSTANCE: DatabaseSingleton? = null

        fun getInstance(): DatabaseSingleton {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = DatabaseSingleton()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}