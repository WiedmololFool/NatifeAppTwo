package com.max.natifeapptwo.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitSingleton private constructor() {

    val userApi: UserApi

    init {
        userApi = configureRetrofit().create(UserApi::class.java)
    }

    private fun configureRetrofit(): Retrofit {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit
    }


    companion object {

        private const val BASE_URL = "https://randomuser.me"

        @Volatile
        private var INSTANCE: RetrofitSingleton? = null

        fun getInstance(): RetrofitSingleton {
            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = RetrofitSingleton()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}