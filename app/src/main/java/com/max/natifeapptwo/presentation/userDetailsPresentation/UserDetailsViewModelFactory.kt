package com.max.natifeapptwo.presentation.userDetailsPresentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.max.natifeapptwo.data.repository.UserListRepository

class UserDetailsViewModelFactory(
    private val uuid: String,
    private val userListRepository: UserListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(uuid = uuid, userListRepository = userListRepository) as T
    }
}