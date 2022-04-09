package com.max.natifeapptwo.app

import android.app.Application
import android.content.Context
import com.max.natifeapptwo.di.AppComponent
import com.max.natifeapptwo.di.AppModule
import com.max.natifeapptwo.di.DaggerAppComponent
import com.max.natifeapptwo.di.DataModule

class App : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .dataModule(DataModule())
            .build()
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }