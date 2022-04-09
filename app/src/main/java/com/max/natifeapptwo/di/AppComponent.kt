package com.max.natifeapptwo.di

import com.max.natifeapptwo.presentation.userDetailsPresentation.UserDetailsFragment
import com.max.natifeapptwo.presentation.userListPresentation.UserListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface AppComponent {
    fun inject(fragment: UserListFragment)
    fun inject(fragment: UserDetailsFragment)
}