package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.project.helpmeat.R
import com.project.helpmeat.constant.Constants
import com.project.helpmeat.controller.GrillSettingsDataController
import com.project.helpmeat.controller.GrillSettingsDataObserver
import com.project.helpmeat.controller.GrillSettingsLayoutController
import com.project.helpmeat.controller.GrillSettingsLayoutController.Step
import com.project.helpmeat.utils.AnimationUtils
import com.project.helpmeat.utils.ResourceUtils
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GrillSettingsFragment : BaseFragment(), GrillSettingsDataObserver {
    companion object {
        const val ALPHA_SHOW = 1.0F
        const val ALPHA_TRANSPARENT = 0.5F
        const val ALPHA_HIDE = 0.0F

        const val BLINK_ANIMATION_DURATION = 500L
    }

    private lateinit var mMeatImage: ImageView
    private lateinit var mMeatButton: TextView
    private lateinit var mWidthButton: TextView
    private lateinit var mGrillButton: TextView
    private lateinit var mStateButton: TextView

    private var mCurrentStep = Step.MEAT

    @Inject
    lateinit var mGrillSettingsDataController: GrillSettingsDataController
    private lateinit var mGrillSettingsLayoutController: GrillSettingsLayoutController

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

        initGrillSettingsControllers(view)
        initSettingMainComponents(view)
        playBlinkAnimation()
    }

    override fun onDestroyView() {
        mGrillSettingsDataController.removeObserverAll()

        super.onDestroyView()
    }

    private fun initGrillSettingsControllers(view: View) {
        mGrillSettingsDataController.addObserver(this)
        mGrillSettingsLayoutController = GrillSettingsLayoutController(requireContext(), mGrillSettingsDataController, view)
    }

    private fun initSettingMainComponents(view: View) {
        mMeatImage = view.findViewById(R.id.fragment_grill_settings_meat_image)

        mMeatButton = view.findViewById(R.id.fragment_grill_settings_meat_button)
        mMeatButton.setOnTouchListener(mOnTouchListener)
        mMeatButton.setOnClickListener {
            mGrillSettingsLayoutController.showLayout(Step.MEAT)
        }

        mWidthButton = view.findViewById(R.id.fragment_grill_settings_width_button)
        mWidthButton.setOnTouchListener(mOnTouchListener)

        mGrillButton = view.findViewById(R.id.fragment_grill_settings_grill_button)
        mGrillButton.setOnTouchListener(mOnTouchListener)

        mStateButton = view.findViewById(R.id.fragment_grill_settings_state_button)
        mStateButton.setOnTouchListener(mOnTouchListener)
    }

    private fun playBlinkAnimation() {
        when (mCurrentStep) {
            Step.MEAT -> {
                AnimationUtils.playBlinkAnimation(BLINK_ANIMATION_DURATION, mMeatButton)
            }
            Step.WIDTH -> {
                AnimationUtils.playBlinkAnimation(BLINK_ANIMATION_DURATION, mWidthButton)
            }
            Step.GRILL -> {
                AnimationUtils.playBlinkAnimation(BLINK_ANIMATION_DURATION, mGrillButton)
            }
            Step.STATE -> {
                AnimationUtils.playBlinkAnimation(BLINK_ANIMATION_DURATION, mStateButton)
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

    override fun onMeatSelected(meatValue: Int) {
        when (Constants.getMeatType(meatValue)) {
            Constants.MeatType.MEAT_TYPE_FORK -> {
                mMeatImage.setImageDrawable(requireContext().getDrawable(R.drawable.fork_with_shadow))
            }
            Constants.MeatType.MEAT_TYPE_BEEF -> {
                mMeatImage.setImageDrawable(requireContext().getDrawable(R.drawable.beef_with_shadow))
            }
            Constants.MeatType.MEAT_TYPE_ERROR -> {}
        }
        mMeatButton.text = ResourceUtils.getMeatName(requireContext(), meatValue)
    }

    override fun onWidthSelected() {
        mWidthButton.text = ""
    }

    override fun onGrillSelected() {
        mGrillButton.text = ""
    }

    override fun onDegreeSelected() {
        mStateButton.text = ""
    }
}