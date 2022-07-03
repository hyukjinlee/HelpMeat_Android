package com.project.helpmeat.utils

import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout

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

        fun getStatusBarHeight(context: Context): Int {
            val resourceId =
                context.resources.getIdentifier("status_bar_height", "dimen", "android");
            return if (resourceId > 0) {
                context.resources.getDimensionPixelOffset(resourceId);
            } else {
                0
            }
        }

        fun getNavigationBarHeight(context: Context): Int {
            val resourceId =
                context.resources.getIdentifier("navigation_bar_height", "dimen", "android");
            return if (resourceId > 0) {
                context.resources.getDimensionPixelOffset(resourceId);
            } else {
                0
            }
        }

        fun setMarginTopBottom(view: View, top: Int, bottom: Int) {
            when (view.parent) {
                is LinearLayout -> {
                    val lp = view.layoutParams as LinearLayout.LayoutParams
                    lp.topMargin = top
                    lp.bottomMargin = bottom
                    view.layoutParams = lp
                }
                is FrameLayout -> {
                    val lp = view.layoutParams as FrameLayout.LayoutParams
                    lp.topMargin = top
                    lp.bottomMargin = bottom
                    view.layoutParams = lp
                }
                is RelativeLayout -> {
                    val lp = view.layoutParams as RelativeLayout.LayoutParams
                    lp.topMargin = top
                    lp.bottomMargin = bottom
                    view.layoutParams = lp
                }
            }
        }
    }
}