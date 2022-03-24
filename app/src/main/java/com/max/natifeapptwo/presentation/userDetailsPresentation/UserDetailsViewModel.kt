package com.max.natifeapptwo.presentation.userDetailsPresentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.max.natifeapptwo.Constants
import com.max.natifeapptwo.data.repository.UserListRepository
import com.max.natifeapptwo.data.room.entities.UserEntity
import com.max.natifeapptwo.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserDetailsViewModel(
    private var uuid: String,
    private val userListRepository: UserListRepository
) : BaseViewModel() {

    private val _user = MutableLiveData<Result<UserEntity>>()
    val user: LiveData<Result<UserEntity>> = _user

    fun getUser() {
        addDisposable(
            userListRepository.findUserByUuid(uuid = uuid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userEntity ->
                    _user.value = runCatching {
                        userEntity
                    }
                }, {
                    Log.e(Constants.TAG, it.message.toString())
                })
        )
    }
}