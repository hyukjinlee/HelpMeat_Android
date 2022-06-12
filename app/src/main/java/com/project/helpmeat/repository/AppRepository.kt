package com.project.helpmeat.repository

import android.database.sqlite.SQLiteConstraintException
import androidx.annotation.WorkerThread
import com.project.helpmeat.repository.db.UserInfo
import com.project.helpmeat.repository.db.UserInfoDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val mUserInfoDAO: UserInfoDAO
) {
    val mUserInfo: Flow<List<UserInfo>> = mUserInfoDAO.observableSelectAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getUserInfo(): UserInfo {
        return mUserInfoDAO.select()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun setUserInfo(userInfo: UserInfo) {
        try {
            mUserInfoDAO.insert(userInfo)
        } catch (e: SQLiteConstraintException) {
            mUserInfoDAO.update(userInfo)
        }
    }
}