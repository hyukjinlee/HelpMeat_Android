package com.project.helpmeat.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import com.project.helpmeat.R
import com.project.helpmeat.navigator.Anim
import com.project.helpmeat.navigator.AppScreens
import com.project.helpmeat.repository.db.UserInfo
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitFragment : BaseFragment() {
    companion object {
        const val SCALE_RATIO = 0.9F
    }

    private lateinit var mInput: EditText
    private var mIsButtonActivated = false

    private lateinit var mScaleUpAnimation: Animation
    private lateinit var mScaleDownAnimation: Animation

    private var mIsItemClicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_init, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val startButton = view.findViewById<ImageButton>(R.id.fragment_init_button)
        mInput = view.findViewById(R.id.fragment_init_input_id)
        mInput.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrBlank()) {
                mIsButtonActivated = false
                startButton.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.black_fire))
            } else {
                mIsButtonActivated = true
                startButton.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.fire_with_message))
            }
        }

        mScaleUpAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_up_large)
        mScaleUpAnimation.fillAfter = true
        mScaleDownAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_down_large)
        mScaleDownAnimation.fillAfter = true

        startButton.setOnTouchListener { v, e ->
            if (mIsButtonActivated && v != null && e != null) {
                when (e.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (!mIsItemClicked) {
                            mIsItemClicked = true
                            v.startAnimation(mScaleDownAnimation)
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (mIsItemClicked) {
                            mAppViewModel.updateUserInfo(UserInfo(0, mInput.text.toString()))
                            mNavigator.navigateTo(AppScreens.MAIN, Anim.SLIDE)
                        }
                    }
                }
            }
            true
        }

        observeDB()
    }

    private fun observeDB() {
        mAppViewModel.mUserInfo.observe(viewLifecycleOwner) { list ->
            list?.let {
                if (it.isNotEmpty()) {
                    mAppViewModel.mUserInfo.removeObservers(viewLifecycleOwner)
                    mInput.setText(it[0].mUserName)
                }
            }
        }
    }
}