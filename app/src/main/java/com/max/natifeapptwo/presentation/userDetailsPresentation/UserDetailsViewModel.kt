package com.max.natifeapptwo.presentation.userDetailsPresentation

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

class UserDetailsViewModel(
    private var uuid: String,
    private val userListRepository: UserListRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<Result<UserEntity>>()
    val user: LiveData<Result<UserEntity>> = _user

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getUser() {
        compositeDisposable.add(
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