package com.max.natifeapptwo.data.room

import android.content.Context
import androidx.room.Room

class DatabaseSingleton private constructor() {

    private lateinit var contextProvider: () -> Context

    val database: UserDatabase by lazy {
        Room.databaseBuilder(
            contextProvider.invoke(),
            UserDatabase::class.java,
            "databaseDb"
        ).build()
    }

    fun init(contextProvider: () -> Context) {
        this.contextProvider = contextProvider
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