package com.max.natifeapptwo.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.max.natifeapptwo.data.retrofit.responseModels.*
import com.max.natifeapptwo.domain.models.UserDetails

@Entity(tableName = UserEntity.TABLE_NAME)
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val age: Int,
    val date: String,
    val email: String,
    val gender: String,
    val city: String,
    val country: String,
    val postcode: Int,
    val state: String,
    val streetName: String,
    val streetNumber: Int,
    val password: String,
    val username: String,
    val uuid: String,
    val first: String,
    val last: String,
    val title: String,
    val nat: String,
    val phone: String,
    val picture: String,
) {

    companion object {
        const val TABLE_NAME = "user_entities_table"
    }
}
