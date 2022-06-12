package com.project.helpmeat.repository.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDAO {
    /**
     *  CREATE, UPDATE
     */
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(userInfo: UserInfo)

    @Update
    suspend fun update(userInfo: UserInfo)

    /**
     *  READ
     */
    @Query("SELECT * FROM user_info")
    fun observableSelectAll(): Flow<List<UserInfo>>

    @Query("SELECT * FROM user_info WHERE `index` == 0")
    suspend fun select(): UserInfo

    /**
     *  DELETE
     */
    @Query("DELETE FROM user_info")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(userInfo: UserInfo)
}