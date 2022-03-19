package com.max.natifeapptwo.data.room

import androidx.room.*
import com.max.natifeapptwo.data.room.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserListDao {

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME}")
    fun loadAllUsers(): Single<List<UserEntity>>

    @Insert
    @JvmSuppressWildcards
    fun saveAllUsers(entities: List<UserEntity>): Completable

    @Query("DELETE FROM ${UserEntity.TABLE_NAME}")
    fun deleteAllUsers(): Completable

    @Query("SELECT * FROM ${UserEntity.TABLE_NAME} WHERE :userId like userId")
    fun loadUser(userId: Int): Single<UserEntity> //в локал дата брать first от листа

    @Insert(entity = UserEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun addUser(userEntity: UserEntity): Completable

    @Delete(entity = UserEntity::class)
    fun deleteUser(userEntity: UserEntity): Completable


}