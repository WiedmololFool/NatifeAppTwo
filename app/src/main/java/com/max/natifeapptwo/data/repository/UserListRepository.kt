package com.max.natifeapptwo.data.repository

import com.max.natifeapptwo.data.room.entities.UserEntity
import io.reactivex.Observable
import io.reactivex.Single


class UserListRepository(
    private val userListLocalDataSource: UserListLocalDataSource,
    private val userListRemoteDataSource: UserListRemoteDataSource
) {

    fun fetchUserList(
        resultsNumber: String,
        nationality: String
    ): Observable<List<UserEntity>> {
        return userListRemoteDataSource
            .getUserList(resultsNumber, nationality)
            .toObservable()
            .map { userListResponse ->
                userListResponse.users.map { user ->
                    user.toUserEntity()
                }
            }
            .flatMap { userList ->
                userListLocalDataSource
                    .deleteAllUsers()
                    .andThen(userListLocalDataSource.saveUsers(userList))
                    .andThen(Observable.just(userList))
            }
            .onErrorResumeNext(userListLocalDataSource.loadAllUsers().toObservable())
    }

    fun findUserByUuid(uuid: String): Single<UserEntity> {
        return userListLocalDataSource.findUserByUuid(uuid = uuid)
    }
}