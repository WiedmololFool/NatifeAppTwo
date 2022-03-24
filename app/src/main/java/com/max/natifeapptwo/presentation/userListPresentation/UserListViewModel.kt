package com.max.natifeapptwo.presentation.userListPresentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.max.natifeapptwo.Constants
import com.max.natifeapptwo.data.repository.UserListRepository
import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserListViewModel(
    private val userListRepository: UserListRepository
) : BaseViewModel() {

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> = _users

    init {
        fetchUserList()
    }

    private fun fetchUserList() {
        addDisposable(
            userListRepository.fetchUserList(
                Constants.USERS_RESPONSE_NUMBER,
                Constants.USERS_RESPONSE_NATIONALITY
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userListResponse ->
                    Log.e(Constants.TAG, userListResponse.toString())
                    _users.value = userListResponse
                }, { throwable ->
                    Log.e(Constants.TAG, throwable.message.toString())
                })
        )
    }
}