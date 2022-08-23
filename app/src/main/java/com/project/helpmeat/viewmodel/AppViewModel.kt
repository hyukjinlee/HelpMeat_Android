package com.project.helpmeat.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.project.helpmeat.repository.AppRepository
import com.project.helpmeat.repository.db.UserInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val mAppRepository: AppRepository,
) : ViewModel() {
    val mUserInfo: LiveData<List<UserInfo>> = mAppRepository.mUserInfo.asLiveData()

    fun updateUserInfo(userInfo: UserInfo) = viewModelScope.launch(Dispatchers.IO) {
        mAppRepository.setUserInfo(userInfo)
    }
}