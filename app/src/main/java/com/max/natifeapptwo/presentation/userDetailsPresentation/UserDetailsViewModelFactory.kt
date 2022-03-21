package com.max.natifeapptwo.presentation.userDetailsPresentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserDetailsViewModelFactory(private val uuid: String) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserDetailsViewModel(uuid = uuid) as T
    }
}