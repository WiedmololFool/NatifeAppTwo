package com.max.natifeapptwo.di

import android.content.Context
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

    single {
        provideRetrofit()
    }

    single {
        provideUserApi(get())
    }

    single {
        provideDatabase(get())
    }

    single {
        provideUserListDao(get())
    }

    single<UserListRemoteDataSource> {
        RetrofitUserListDataSource(userApi = get())
    }

    single<UserListLocalDataSource> {
        RoomUserListDataSource(userListDao = get())
    }

    single {
        UserListRepository(
            userListRemoteDataSource = get(),
            userListLocalDataSource = get()
        )
    }
}

fun provideRetrofit(): Retrofit {
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

    return retrofit
}

fun provideUserApi(retrofit: Retrofit): UserApi {
    return retrofit.create(UserApi::class.java)
}

fun provideDatabase(context: Context): UserDatabase {
    val database: UserDatabase by lazy {
        Room.databaseBuilder(
            context,
            UserDatabase::class.java,
            "databaseDb"
        ).build()
    }
    return database
}

fun provideUserListDao(database: UserDatabase): UserListDao {
    return database.userListDao()
}