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

        fun getMeatSettingDescription(context: Context): Array<String> {
            val res = context.resources

            val first = res.getString(R.string.meat_description_text_first)
            val second = res.getString(R.string.meat_description_text_second)
            val third = res.getString(R.string.meat_description_text_third)

            return arrayOf(first, second, third)
        }

        fun getWidthSettingDescription(context: Context): Array<String> {
            val res = context.resources

            val first = res.getString(R.string.width_description_text_first)
            val second = res.getString(R.string.width_description_text_second)
            val third = res.getString(R.string.width_description_text_third)

            return arrayOf(first, second, third)
        }

        fun getGrillSettingDescription(context: Context): Array<String> {
            val res = context.resources

            val second = res.getString(R.string.grill_description_text_second)
            val third = res.getString(R.string.grill_description_text_third)

            return arrayOf("", second, third)
        }

        fun getDegreeSettingDescription(context: Context): Array<String> {
            val res = context.resources

            val first = res.getString(R.string.degree_description_text_first)
            val second = res.getString(R.string.degree_description_text_second)
            val third = res.getString(R.string.degree_description_text_third)

            return arrayOf(first, second, third)
        }

        fun getFinishDescription(context: Context): Array<String> {
            val res = context.resources

            val first = res.getString(R.string.finish_description_text_first)
            val third = res.getString(R.string.finish_description_text_third)

            return arrayOf(first, "", third)
        }
    }
}