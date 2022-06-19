package com.project.helpmeat.utils

import android.view.View

class ViewUtils {
    companion object {
        fun scaleUp(v: View, scaleRatio: Float) {
            val lp = v.layoutParams
            lp.width = (v.width / scaleRatio).toInt()
            lp.height = (v.height / scaleRatio).toInt()
            v.layoutParams = lp
        }

        fun scaleDown(v: View, scaleRatio: Float) {
            val lp = v.layoutParams
            lp.width = (v.width * scaleRatio).toInt()
            lp.height = (v.height * scaleRatio).toInt()
            v.layoutParams = lp
        }
    }
}