package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import com.project.helpmeat.R
import com.project.helpmeat.navigator.AppScreens
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment() {
    companion object {
        const val ANIMATION_DURATION = 1500L
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val animationView = view.findViewById<FrameLayout>(R.id.fragment_intro_animation_view)
        val alphaAnimation = AlphaAnimation(1.0f, 0.0f).apply {
            startOffset = 0
            duration = ANIMATION_DURATION
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {
                    animationView.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animation?) {
                    animationView.visibility = View.GONE
                    mNavigator.navigateTo(AppScreens.MAIN)
                }

                override fun onAnimationRepeat(animation: Animation?) {
                    // Do nothing
                }
            })
        }
        animationView.startAnimation(alphaAnimation)
    }
}