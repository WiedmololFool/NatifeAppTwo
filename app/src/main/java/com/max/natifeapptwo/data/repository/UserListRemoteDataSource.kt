package com.max.natifeapptwo.data.repository

import com.max.natifeapptwo.data.retrofit.responseModels.UserListResponse
import io.reactivex.Single
import retrofit2.Call

interface UserListRemoteDataSource {

    fun getUserList(resultsNumber:String, nationality: String): Single<UserListResponse>


}