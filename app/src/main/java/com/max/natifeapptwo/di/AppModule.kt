package com.max.natifeapptwo.di

import com.max.natifeapptwo.presentation.userDetailsPresentation.UserDetailsViewModel
import com.max.natifeapptwo.presentation.userListPresentation.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<UserListViewModel> {
        UserListViewModel(userListRepository = get())
    }

    viewModel<UserDetailsViewModel>{
        (uuid: String) -> UserDetailsViewModel(uuid = uuid, get())
    }
}