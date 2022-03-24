package com.max.natifeapptwo.data.room

import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.data.repository.UserListLocalDataSource
import io.reactivex.Completable
import io.reactivex.Single

class RoomUserListDataSource(
    private val userListDao: UserListDao
) : UserListLocalDataSource {

    override fun loadAllUsers(): Single<List<UserEntity>> {
        return userListDao.loadAllUsers()
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