package com.max.natifeapptwo.data.retrofit

import com.max.natifeapptwo.data.retrofit.responseModels.UserListResponse
import com.max.natifeapptwo.data.repository.UserListRemoteDataSource
import io.reactivex.Single


class RetrofitUserListDataSource(private val userApi: UserApi) : UserListRemoteDataSource {

    override fun getUserList(
        resultsNumber: String,
        nationality: String
    ): Single<UserListResponse> = userApi.getUserList(resultsNumber, nationality)
}