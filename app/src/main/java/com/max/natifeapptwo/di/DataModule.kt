package com.max.natifeapptwo.di

import androidx.room.Room
import com.max.natifeapptwo.Constants
import com.max.natifeapptwo.data.repository.UserListLocalDataSource
import com.max.natifeapptwo.data.repository.UserListRemoteDataSource
import com.max.natifeapptwo.data.repository.UserListRepository
import com.max.natifeapptwo.data.retrofit.RetrofitUserListDataSource
import com.max.natifeapptwo.data.retrofit.UserApi
import com.max.natifeapptwo.data.room.RoomUserListDataSource
import com.max.natifeapptwo.data.room.UserDatabase
import com.max.natifeapptwo.data.room.UserListDao
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<UserApi> {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        retrofit.create(UserApi::class.java)
    }

    single<UserListDao>{
        val database: UserDatabase by lazy {
            Room.databaseBuilder(
                get(),
                UserDatabase::class.java,
                "databaseDb"
            ).build()
        }
        database.userListDao()
    }

    single<UserListRemoteDataSource> {
        RetrofitUserListDataSource(userApi = get())
    }

    single<UserListLocalDataSource> {
        RoomUserListDataSource(userListDao = get())
    }

    single<UserListRepository> {
        UserListRepository(
            userListRemoteDataSource = get(),
            userListLocalDataSource = get()
        )
    }
}