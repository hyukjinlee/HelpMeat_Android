package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.RelativeLayout
import android.widget.TextView
import com.project.helpmeat.R
import com.project.helpmeat.controller.GrillSettingsLayoutController
import com.project.helpmeat.controller.GrillSettingsLayoutController.STEP
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.function.Consumer

@AndroidEntryPoint
class GrillSettingsFragment : BaseFragment() {
    companion object {
        const val ALPHA_SHOW = 1.0F
        const val ALPHA_TRANSPARENT = 0.5F
        const val ALPHA_HIDE = 0.0F

        const val BLINK_ANIMATION_DURATION = 500L
    }

    private lateinit var mMeatButton: TextView
    private lateinit var mWidthButton: TextView
    private lateinit var mGrillButton: TextView
    private lateinit var mStateButton: TextView

    private lateinit var mBlinkAnimation: Animation

    private var mCurrentStep = STEP.MEAT

    private lateinit var mGrillSettingsLayoutController: GrillSettingsLayoutController
    private val mOnMeatSelectedListener: Consumer<String> = Consumer { selectedText ->
        mMeatButton.text = selectedText
    }
    private val mOnWidthSelectedListener: Consumer<String> = Consumer { selectedText ->
        mWidthButton.text = selectedText
    }
    private val mOnGrillSelectedListener: Consumer<String> = Consumer { selectedText ->
        mGrillButton.text = selectedText
    }
    private val mOnStateSelectedListener: Consumer<String> = Consumer { selectedText ->
        mStateButton.text = selectedText
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_grill_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = view.findViewById<RelativeLayout>(R.id.fragment_grill_settings_content_container)
        limitContentViewArea(root, false)

        initGrillSettingsLayoutController(view)
        initButtons(view)
        initAndRunBlinkAnimation()
    }

    private fun initGrillSettingsLayoutController(view: View) {
        mGrillSettingsLayoutController = GrillSettingsLayoutController(requireContext(), view)
        mGrillSettingsLayoutController.setOnSelectedListeners(
            mOnMeatSelectedListener, mOnWidthSelectedListener,
            mOnGrillSelectedListener, mOnStateSelectedListener
        )
    }

    private fun initButtons(view: View) {
        mMeatButton = view.findViewById(R.id.fragment_grill_settings_meat_button)
        mMeatButton.setOnTouchListener(mOnTouchListener)
        mMeatButton.setOnClickListener {
            mGrillSettingsLayoutController.showLayout(STEP.MEAT)
        }

        mWidthButton = view.findViewById(R.id.fragment_grill_settings_width_button)
        mWidthButton.setOnTouchListener(mOnTouchListener)

        mGrillButton = view.findViewById(R.id.fragment_grill_settings_grill_button)
        mGrillButton.setOnTouchListener(mOnTouchListener)

        mStateButton = view.findViewById(R.id.fragment_grill_settings_state_button)
        mStateButton.setOnTouchListener(mOnTouchListener)
    }

    private fun initAndRunBlinkAnimation() {
        mBlinkAnimation = AlphaAnimation(ALPHA_TRANSPARENT, ALPHA_SHOW).apply {
            duration = BLINK_ANIMATION_DURATION
            repeatCount = Animation.INFINITE
            repeatMode = Animation.REVERSE
        }
        runBlinkAnimation()
    }

    private fun runBlinkAnimation() {
        when (mCurrentStep) {
            STEP.MEAT -> {
                mMeatButton.startAnimation(mBlinkAnimation)
            }
            STEP.WIDTH -> {
                mWidthButton.startAnimation(mBlinkAnimation)
            }
            STEP.GRILL -> {
                mGrillButton.startAnimation(mBlinkAnimation)
            }
            STEP.STATE -> {
                mStateButton.startAnimation(mBlinkAnimation)
            }
        }
    }

    private fun onStepCompleted(btn: TextView) {
        context?.let {
            btn.clearAnimation()
            btn.background = it.getDrawable(R.drawable.bg_rounded_rectangle_50_pink)
            btn.setTextColor(it.getColor(R.color.white))
        }
    }

    override fun needTouchAnimation() = true
}