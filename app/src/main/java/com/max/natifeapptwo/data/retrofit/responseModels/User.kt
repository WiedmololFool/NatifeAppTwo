package com.max.natifeapptwo.data.retrofit.responseModels

import com.max.natifeapptwo.data.room.entities.UserEntity

data class User(
    val dob: Dob,
    val email: String,
    val gender: String,
    val location: Location,
    val login: Login,
    val name: Name,
    val nat: String,
    val phone: String,
    val picture: Picture,
){
    fun toUserEntity(): UserEntity {
        return UserEntity(
            userId = 0,
            age = dob.age,
            date = dob.date,
            email = email,
            gender = gender,
            city = location.city,
            country = location.country,
            postcode = location.postcode,
            state = location.state,
            streetName = location.street.name,
            streetNumber = location.street.number,
            password = login.password,
            username = login.username,
            uuid = login.uuid,
            first = name.first,
            last = name.last,
            title = name.title,
            nat = nat,
            phone = phone,
            picture = picture.medium
            )
    }
}