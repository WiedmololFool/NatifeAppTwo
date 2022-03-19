package com.max.natifeapptwo.data.repository

import com.max.natifeapptwo.data.retrofit.responseModels.UserListResponse
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
    ): Observable<UserListResponse> {

        userListLocalDataSource.deleteAllUsers()

        return userListRemoteDataSource
            .getUserList(resultsNumber, nationality)
            .toObservable()
            .flatMap { userListResponse ->
                userListLocalDataSource
                    .saveRemoteResponse(userListResponse)
                    .andThen(Observable.just(userListResponse))
            }
    }

    fun getCachedUserList(): Single<List<UserEntity>> {
        return userListLocalDataSource.loadAllUsers()
    }


}