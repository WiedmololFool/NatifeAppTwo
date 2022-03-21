package com.max.natifeapptwo.data.repository

import com.max.natifeapptwo.data.retrofit.responseModels.UserListResponse
import io.reactivex.Single

interface UserListRemoteDataSource {

    fun getUserList(resultsNumber:String, nationality: String): Single<UserListResponse>


}