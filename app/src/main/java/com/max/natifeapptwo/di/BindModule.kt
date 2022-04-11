package com.max.natifeapptwo.di

import com.max.natifeapptwo.data.repository.UserListLocalDataSource
import com.max.natifeapptwo.data.repository.UserListRemoteDataSource
import com.max.natifeapptwo.data.retrofit.RetrofitUserListDataSource
import com.max.natifeapptwo.data.room.RoomUserListDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface BindModule {

    @Binds
    fun bindRetrofitUserListDataSource(
        retrofitUserListDataSource: RetrofitUserListDataSource
    ): UserListRemoteDataSource

    @Binds
    fun bindRoomUserListDataSource(
        roomUserListDataSource: RoomUserListDataSource
    ): UserListLocalDataSource


}