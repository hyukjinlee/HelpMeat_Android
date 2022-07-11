package com.project.helpmeat.utils

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.project.helpmeat.R
import com.project.helpmeat.view.GrillSettingsFragment

class AnimationUtils {
    companion object {
        fun playBlinkAnimation(duration: Long, view: View) {
            val animation = AlphaAnimation(GrillSettingsFragment.ALPHA_TRANSPARENT, GrillSettingsFragment.ALPHA_SHOW).apply {
                this.duration = duration
                repeatCount = Animation.INFINITE
                repeatMode = Animation.REVERSE
            }
            view.startAnimation(animation)
        }


        fun playMoveLeftAnimation(context: Context, exitView: View, enterView: View) {
            val exitToLeftDownAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.exit_to_left)
            val enterFromRightDownAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.enter_from_right)

            exitView.visibility = View.VISIBLE
            setGoneListener(exitView, exitToLeftDownAnimation)
            exitView.startAnimation(exitToLeftDownAnimation)

            enterView.visibility = View.VISIBLE
            enterView.startAnimation(enterFromRightDownAnimation)
        }

        fun playMoveRightAnimation(context: Context, exitView: View, enterView: View) {
            val exitToRightDownAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.exit_to_right)
            val enterFromLeftDownAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.enter_from_left)

            exitView.visibility = View.VISIBLE
            setGoneListener(exitView, exitToRightDownAnimation)
            exitView.startAnimation(exitToRightDownAnimation)

            enterView.visibility = View.VISIBLE
            enterView.startAnimation(enterFromLeftDownAnimation)
        }

        fun playFullScaleUpAnimation(context: Context, view: View) {
            val fullScaleUpAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_up_full)

            view.visibility = View.VISIBLE
            view.startAnimation(fullScaleUpAnimation)
        }

        fun playFullScaleDownAnimation(context: Context, view: View) {
            val fullScaleDownAnimation: Animation = AnimationUtils.loadAnimation(context, R.anim.scale_down_full)

            view.visibility = View.VISIBLE
            setGoneListener(view, fullScaleDownAnimation)
            view.startAnimation(fullScaleDownAnimation)
        }

        fun setGoneListener(view: View, animation: Animation) {
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                }

                override fun onAnimationEnd(animation: Animation?) {
                    view.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation?) {
                }
            })
        }
    }
}