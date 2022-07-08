package com.project.helpmeat.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.project.helpmeat.R
import com.project.helpmeat.navigator.Anim
import com.project.helpmeat.navigator.AppScreens
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroFragment : BaseFragment() {
    companion object {
        const val ANIMATION_DELAY = 1000L
        const val ANIMATION_DURATION = 2000L

        const val ALPHA_SHOW = 1.0F
        const val ALPHA_HIDE = 0.0F
    }

    private var mNeedToInit = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeDB()
        val introFirst = view.findViewById<RelativeLayout>(R.id.fragment_intro_first)
        val introSecond = view.findViewById<RelativeLayout>(R.id.fragment_intro_second)
        val animatorSet = AnimatorSet().apply {
            duration = ANIMATION_DURATION
            startDelay = ANIMATION_DELAY
            playTogether(
                ObjectAnimator.ofFloat(introFirst, "alpha", ALPHA_SHOW, ALPHA_HIDE),
                ObjectAnimator.ofFloat(introSecond, "alpha", ALPHA_HIDE, ALPHA_SHOW)
            )
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p0: Animator?) {
                    // Do nothing
                }

                override fun onAnimationEnd(p0: Animator?) {
                    view.postDelayed({
                        if (mNeedToInit) {
                            mNavigator.navigateTo(AppScreens.INIT, Anim.SLIDE)
                        } else {
                            mNavigator.navigateTo(AppScreens.MAIN, Anim.SLIDE)
                        }
                    }, ANIMATION_DELAY)
                }

                override fun onAnimationCancel(p0: Animator?) {
                    // Do nothing
                }

                override fun onAnimationRepeat(p0: Animator?) {
                    // Do nothing
                }
            })
        }
        animatorSet.start()
    }

    private fun observeDB() {
        mAppViewModel.mUserInfo.observe(viewLifecycleOwner) { list ->
            list?.let {
                if (it.isNotEmpty()) {
                    mAppViewModel.mUserInfo.removeObservers(viewLifecycleOwner)
                    mNeedToInit = false
                }
            }
        }
    }
}