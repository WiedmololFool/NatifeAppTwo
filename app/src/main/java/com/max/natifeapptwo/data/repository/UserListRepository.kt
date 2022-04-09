package com.max.natifeapptwo.data.repository

import com.max.natifeapptwo.data.room.entities.UserEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserListRepository @Inject constructor(
    private val userListLocalDataSource: UserListLocalDataSource,
    private val userListRemoteDataSource: UserListRemoteDataSource,
) {
    private var firstRequest = true

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
                if (firstRequest) {
                    firstRequest = false
                    userListLocalDataSource
                        .deleteAllUsers()
                        .andThen(userListLocalDataSource.saveUsers(userList))
                        .andThen(Observable.just(userList))
                } else {
                    userListLocalDataSource.saveUsers(userList)
                        .andThen(Observable.just(userList))
                }
            }
            .onErrorResumeNext(userListLocalDataSource.loadAllUsers().toObservable())
    }

    fun findUserByUuid(uuid: String): Single<UserEntity> {
        return userListLocalDataSource.findUserByUuid(uuid = uuid)
    }
}