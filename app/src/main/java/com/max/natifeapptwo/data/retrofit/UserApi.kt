package com.max.natifeapptwo.data.retrofit

import com.max.natifeapptwo.data.retrofit.responseModels.UserListResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("/api/")
    fun getUserList(
        @Query("results") resultsNumber: String,
        @Query("nat") nationality: String
    ): Single<UserListResponse>
}