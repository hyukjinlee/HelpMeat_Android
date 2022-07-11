package com.project.helpmeat.controller

import android.content.Context
import android.view.View
import android.widget.FrameLayout
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
) : GrillSettingsDataObserver {
    companion object {
        const val TAG = "GrillSettingsLayoutController"
    }

    enum class Step {
        MEAT,
        WIDTH,
        GRILL,
        STATE
    }

    private val mMainLayout: FrameLayout
    private lateinit var mMeatLayout: ConstraintLayout

    private lateinit var mMeatLayoutTopContainer: LinearLayout // Fork
    private lateinit var mMeatLayoutBottomContainer: LinearLayout // Beef

    private lateinit var mMeatDetailLayout: LinearLayout
    private lateinit var mMeatDetailBackButton: TextView
    private lateinit var mDetailMeatList: RecyclerView
    private lateinit var mDetailMeatListAdapter: MeatListAdapter

    private lateinit var mCurrentStep: Step

    init {
        mMainLayout = view.findViewById(R.id.fragment_grill_settings_main)
        mGrillSettingsDataController.addObserver(this)

        initMeatLayout(view)
    }

    private fun initMeatLayout(view: View) {
        mMeatLayout = view.findViewById(R.id.fragment_grill_settings_meat)

        mMeatLayoutTopContainer = view.findViewById(R.id.fragment_grill_settings_meat_top_container)
        mMeatLayoutTopContainer.setOnClickListener {
            mDetailMeatListAdapter.updateMeatType(MeatType.MEAT_TYPE_FORK)
            AnimationUtils.playMoveLeftAnimation(mContext, mMeatLayout, mMeatDetailLayout)
        }

        mMeatLayoutBottomContainer = view.findViewById(R.id.fragment_grill_settings_meat_bottom_container)
        mMeatLayoutBottomContainer.setOnClickListener {
            mDetailMeatListAdapter.updateMeatType(MeatType.MEAT_TYPE_BEEF)
            AnimationUtils.playMoveLeftAnimation(mContext, mMeatLayout, mMeatDetailLayout)
        }

        mMeatDetailLayout = view.findViewById(R.id.fragment_grill_settings_meat_detail)
        mMeatDetailBackButton = view.findViewById(R.id.fragment_grill_settings_meat_detail_back_button)
        mMeatDetailBackButton.setOnClickListener {
            AnimationUtils.playMoveRightAnimation(mContext, mMeatDetailLayout, mMeatLayout)
        }

        mDetailMeatList = view.findViewById(R.id.fragment_grill_settings_meat_detail_list)
        mDetailMeatListAdapter = MeatListAdapter(mContext)
        mDetailMeatListAdapter.setGrillSettingsDataController(mGrillSettingsDataController)
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
            Step.STATE -> {
                AnimationUtils.playFullScaleUpAnimation(mContext, mMeatLayout)
            }
        }
    }

    private fun hideLayout(layout: View) {
        AnimationUtils.playFullScaleDownAnimation(mContext, layout)
    }

    override fun onMeatSelected(meatType: Int) {
        hideLayout(mMeatDetailLayout)
    }

    override fun onWidthSelected() {
        TODO("Not yet implemented")
    }

    override fun onGrillSelected() {
        TODO("Not yet implemented")
    }

    override fun onDegreeSelected() {
        TODO("Not yet implemented")
    }
}