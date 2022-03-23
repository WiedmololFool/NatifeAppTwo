package com.max.natifeapptwo.data.room

import android.content.Context
import androidx.room.Room

object DatabaseObject {

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
}