package com.max.natifeapptwo.presentation.userListPresentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.max.natifeapptwo.data.repository.UserListRepository
import com.max.natifeapptwo.data.room.entities.UserEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserListViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val _users = MutableLiveData<List<UserEntity>>()
    val users: LiveData<List<UserEntity>> = _users

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun fetchUserList(userListRepository: UserListRepository) {
        compositeDisposable.add(
            userListRepository.fetchUserList(
                "20",
                "au,br,ch,de,dk,es,fi,fr,ie,no,nl,nz,tr,us"
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userListResponse ->
                    Log.e("TAG", userListResponse.users[0].name.toString())
                    _users.value = userListResponse.users.map {
                        it.toUserEntity()
                    }
                    userListRepository.clearCachedUserList().subscribe({
                        Log.e("TAG", "Success clearCachedUserList()")
                    },{

                    })
                }, { throwable ->
                    compositeDisposable.add(
                        userListRepository.getCachedUserList().subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ userEntities ->
                                _users.value = userEntities
                            }, {
                                Log.e("TAG", throwable.message.toString())
                            })
                    )
                    Log.e("TAG", throwable.message.toString())
                })
        )
    }
}