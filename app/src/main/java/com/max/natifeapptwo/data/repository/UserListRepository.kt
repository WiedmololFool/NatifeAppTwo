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
            .flatMap { userListResponse ->
                userListLocalDataSource
                    .deleteAllUsers()
                    .andThen(userListLocalDataSource.saveUsers(
                        userListResponse.users.map {
                        it.toUserEntity()
                    }))
                    .andThen(Observable.just(userListResponse.users.map {
                        it.toUserEntity()
                    }))
            }
            .onErrorResumeNext(userListLocalDataSource.loadAllUsers().toObservable())
    }

    fun findUserByUuid(uuid: String): Single<UserEntity> {
        return userListLocalDataSource.findUserByUuid(uuid = uuid)
    }
}