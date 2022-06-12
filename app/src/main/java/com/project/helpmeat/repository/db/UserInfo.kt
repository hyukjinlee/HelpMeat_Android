package com.project.helpmeat.repository.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey
    @ColumnInfo(name = "index") var mIndex: Int,

    @ColumnInfo(name = "user_name") var mUserName: String,
)