package com.project.helpmeat.utils

import android.content.Context
import com.project.helpmeat.R
import com.project.helpmeat.constant.Constants

class ResourceUtils {
    companion object {
        fun getForkList(context: Context): List<String> = context.resources.getStringArray(R.array.fork_list).toList()
        fun getBeefList(context: Context): List<String> = context.resources.getStringArray(R.array.beef_list).toList()

        fun getMeatName(context: Context, meatValue: Int): String {
            val meatType = Constants.getMeatType(meatValue)
            val index = Constants.getMeatIndex(meatValue)
            val meatName = when (meatType) {
                Constants.MeatType.MEAT_TYPE_FORK -> {
                    val forkList = getForkList(context)
                    "${forkList[index]}\n(돼지)"
                }
                Constants.MeatType.MEAT_TYPE_BEEF -> {
                    val beefList = getBeefList(context)
                    "${beefList[index]}\n(소)"
                }
                Constants.MeatType.MEAT_TYPE_ERROR -> {
                    "오류"
                }
            }

            return meatName
        }
    }
}