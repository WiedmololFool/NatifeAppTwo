package com.max.natifeapptwo.data.room

import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.data.repository.UserListLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class RoomUserListDataSource @Inject constructor(
    private val userListDao: UserListDao
) : UserListLocalDataSource {

    private var firstRequest = true

    override fun loadAllUsers(): Single<List<UserEntity>> {
        return if (firstRequest) {
            firstRequest = false
            userListDao.loadAllUsers()
        } else {
            Single.just(listOf())
        }
    }

    override fun loadUser(userId: Int): Single<UserEntity> {
        return userListDao.loadUser(userId = userId)
    }

    override fun findUserByUuid(uuid: String): Single<UserEntity> {
        return userListDao.findUserByUuid(uuid = uuid)
    }

    override fun deleteAllUsers(): Completable {
        return userListDao.deleteAllUsers()
    }

    override fun saveUsers(userList: List<UserEntity>): Completable {
        return userListDao.saveAllUsers(userList)
    }
}