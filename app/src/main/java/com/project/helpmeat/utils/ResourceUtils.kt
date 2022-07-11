package com.project.helpmeat.utils

import android.content.Context
import com.project.helpmeat.R
import com.project.helpmeat.constant.Constants

class ResourceUtils {
    companion object {
        fun getForkList(context: Context): List<String> = context.resources.getStringArray(R.array.fork_list).toList()
        fun getBeefList(context: Context): List<String> = context.resources.getStringArray(R.array.beef_list).toList()

        fun getMeatName(context: Context, mt: Int): String {
            val meatType = mt / Constants.MEAT_TYPE_STANDARD * Constants.MEAT_TYPE_STANDARD
            val index = (mt % Constants.MEAT_TYPE_STANDARD)
            val meatName = when (meatType) {
                Constants.MeatType.MEAT_TYPE_FORK.value -> {
                    val forkList = getForkList(context)
                    "${forkList[index]}\n(돼지)"
                }
                Constants.MeatType.MEAT_TYPE_BEEF.value -> {
                    val beefList = getBeefList(context)
                    "${beefList[index]}\n(소)"
                }
                else -> ""
            }

            return meatName
        }
    }
}