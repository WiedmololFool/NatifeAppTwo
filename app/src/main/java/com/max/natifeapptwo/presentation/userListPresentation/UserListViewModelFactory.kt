package com.max.natifeapptwo.presentation.userListPresentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.max.natifeapptwo.data.repository.UserListRepository

class UserListViewModelFactory(
    private val userListRepository: UserListRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserListViewModel(userListRepository = userListRepository) as T
    }
}