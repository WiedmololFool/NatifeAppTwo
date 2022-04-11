package com.max.natifeapptwo.di

import android.content.Context
import androidx.room.Room
import com.max.natifeapptwo.Constants
import com.max.natifeapptwo.data.repository.UserListLocalDataSource
import com.max.natifeapptwo.data.repository.UserListRemoteDataSource
import com.max.natifeapptwo.data.retrofit.RetrofitUserListDataSource
import com.max.natifeapptwo.data.retrofit.UserApi
import com.max.natifeapptwo.data.room.RoomUserListDataSource
import com.max.natifeapptwo.data.room.UserDatabase
import com.max.natifeapptwo.data.room.UserListDao
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class DataModule {

    @Provides
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

    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
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

    @Provides
    fun provideUserListDao(database: UserDatabase): UserListDao {
        return database.userListDao()
    }

    @Provides
    fun provideUserListLocalDataSource(userListDao: UserListDao): UserListLocalDataSource {
        return RoomUserListDataSource(userListDao = userListDao)
    }

    @Provides
    fun provideUserListRemoteDataSource(userApi: UserApi): UserListRemoteDataSource {
        return RetrofitUserListDataSource(userApi = userApi)
    }
}