package com.project.helpmeat.controller

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.project.helpmeat.R
import com.project.helpmeat.components.MeatListAdapter
import com.project.helpmeat.constant.Constants.MeatType
import com.project.helpmeat.utils.AnimationUtils

class GrillSettingsLayoutController(
    private val mContext: Context,
    private var mGrillSettingsDataController: GrillSettingsDataController,
    view: View,
) {
    companion object {
        const val TAG = "GrillSettingsLayoutController"
    }

    enum class Step {
        MEAT,
        WIDTH,
        GRILL,
        DEGREE,
        FINISH
    }

    private lateinit var mOKButton: Button
    private lateinit var mMeatLayout: ConstraintLayout

    private lateinit var mMeatLayoutTopContainer: LinearLayout // Fork
    private lateinit var mMeatLayoutBottomContainer: LinearLayout // Beef

    private lateinit var mMeatDetailLayout: LinearLayout
    private lateinit var mMeatDetailBackButton: TextView
    private lateinit var mDetailMeatList: RecyclerView
    private lateinit var mDetailMeatListAdapter: MeatListAdapter

    private lateinit var mCurrentStep: Step

    init {
        initOKButton(view)
        initMeatLayout(view)
    }

    private fun initOKButton(view: View) {
        mOKButton = view.findViewById(R.id.fragment_grill_settings_ok_button)
        mOKButton.setOnClickListener {
            when (mCurrentStep) {
                Step.MEAT -> {
                    mGrillSettingsDataController.notifyMeatCompleted()
                    hideLayout(mMeatDetailLayout)
                }
                Step.WIDTH -> {

                }
                Step.GRILL -> {

                }
                Step.DEGREE -> {

                }
                Step.FINISH -> {

                }
            }
        }
    }

    private fun initMeatLayout(view: View) {
        mMeatLayout = view.findViewById(R.id.layout_grill_settings_meat)

        mMeatLayoutTopContainer = view.findViewById(R.id.layout_grill_settings_meat_top_container)
        mMeatLayoutTopContainer.setOnClickListener {
            mDetailMeatListAdapter.updateMeatType(MeatType.MEAT_TYPE_FORK)
            AnimationUtils.playMoveLeftAnimation(mContext, mMeatLayout, mMeatDetailLayout)
            showOKButton()
        }

        mMeatLayoutBottomContainer = view.findViewById(R.id.layout_grill_settings_meat_bottom_container)
        mMeatLayoutBottomContainer.setOnClickListener {
            mDetailMeatListAdapter.updateMeatType(MeatType.MEAT_TYPE_BEEF)
            AnimationUtils.playMoveLeftAnimation(mContext, mMeatLayout, mMeatDetailLayout)
            showOKButton()
        }

        mMeatDetailLayout = view.findViewById(R.id.fragment_grill_settings_meat_detail)
        mMeatDetailBackButton = view.findViewById(R.id.fragment_grill_settings_meat_detail_back_button)
        mMeatDetailBackButton.setOnClickListener {
            AnimationUtils.playMoveRightAnimation(mContext, mMeatDetailLayout, mMeatLayout)
            hideOKButton()
        }

        mDetailMeatList = view.findViewById(R.id.fragment_grill_settings_meat_detail_list)
        mDetailMeatListAdapter = MeatListAdapter(mContext, mGrillSettingsDataController)
        mDetailMeatList.adapter = mDetailMeatListAdapter
    }

    fun showLayout(step: Step) {
        mCurrentStep = step
        when (step) {
            Step.MEAT -> {
                AnimationUtils.playFullScaleUpAnimation(mContext, mMeatLayout)
            }
            Step.WIDTH -> {
                AnimationUtils.playFullScaleUpAnimation(mContext, mMeatLayout)
            }
            Step.GRILL -> {
                AnimationUtils.playFullScaleUpAnimation(mContext, mMeatLayout)
            }
            Step.DEGREE -> {
                AnimationUtils.playFullScaleUpAnimation(mContext, mMeatLayout)
            }
            Step.FINISH -> {}
        }
    }

    private fun hideLayout(layout: View) {
        AnimationUtils.playFullScaleDownAnimation(mContext, layout)
        hideOKButton()
    }

    private fun showOKButton() {
        mOKButton.visibility = View.VISIBLE
    }

    private fun hideOKButton() {
        mOKButton.visibility = View.GONE
    }
}