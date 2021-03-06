package com.max.natifeapptwo.data.repository

import com.max.natifeapptwo.data.room.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Single


interface UserListLocalDataSource {

    fun loadAllUsers(): Single<List<UserEntity>>

    fun loadUser(userId: Int): Single<UserEntity>

    fun findUserByUuid(uuid: String): Single<UserEntity>

    fun deleteAllUsers(): Completable

    fun saveUsers(userList: List<UserEntity>): Completable
}