package com.project.helpmeat.utils

import android.content.Context
import com.project.helpmeat.R

class ResourceUtils {
    companion object {
        fun getForkList(context: Context): List<String> = context.resources.getStringArray(R.array.fork_list).toList()
        fun getBeefList(context: Context): List<String> = context.resources.getStringArray(R.array.beef_list).toList()
    }
}