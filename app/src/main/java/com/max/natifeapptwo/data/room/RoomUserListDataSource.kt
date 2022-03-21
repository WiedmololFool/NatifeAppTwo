package com.max.natifeapptwo.data.room

import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.data.repository.UserListLocalDataSource
import com.max.natifeapptwo.data.retrofit.responseModels.UserListResponse
import io.reactivex.Completable
import io.reactivex.Single

class RoomUserListDataSource(
    private val userListDao: UserListDao
) : UserListLocalDataSource {  //add userListDao

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



    override fun saveRemoteResponse(response: UserListResponse): Completable {
        return userListDao.saveAllUsers(response.users.map { user ->
            user.toUserEntity()
        })
    }
}