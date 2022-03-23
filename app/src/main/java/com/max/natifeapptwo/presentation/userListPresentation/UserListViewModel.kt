package com.max.natifeapptwo.presentation.userListPresentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.max.natifeapptwo.Constants
import com.max.natifeapptwo.data.repository.UserListRepository
import com.max.natifeapptwo.data.room.entities.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserListViewModel(
    userListRepository: UserListRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> = _users

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    init {
        fetchUserList(userListRepository = userListRepository)
    }

    private fun fetchUserList(userListRepository: UserListRepository) {
        compositeDisposable.add(
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