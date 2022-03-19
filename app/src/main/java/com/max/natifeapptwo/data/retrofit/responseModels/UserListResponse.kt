package com.max.natifeapptwo.data.retrofit.responseModels

import com.google.gson.annotations.SerializedName

data class UserListResponse(

    @SerializedName("results")
    val users: List<User>
)