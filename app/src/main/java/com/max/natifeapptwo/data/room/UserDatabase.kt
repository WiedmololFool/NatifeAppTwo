package com.max.natifeapptwo.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.max.natifeapptwo.data.room.entities.UserEntity


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = true
)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userListDao(): UserListDao

}