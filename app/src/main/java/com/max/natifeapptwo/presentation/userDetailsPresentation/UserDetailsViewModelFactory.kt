package com.max.natifeapptwo.presentation.userDetailsPresentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.max.natifeapptwo.data.repository.UserListRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class UserDetailsViewModelFactory @AssistedInject constructor(
    @Assisted("uuid") private val uuid: String,
    private val userListRepository: UserListRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(uuid = uuid, userListRepository = userListRepository) as T
    }

    @AssistedFactory
    interface Factory{

        fun create(@Assisted("uuid") uuid: String): UserDetailsViewModelFactory
    }
}