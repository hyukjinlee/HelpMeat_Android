package com.project.helpmeat.controller

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.project.helpmeat.R
import com.project.helpmeat.components.MeatListAdapter
import com.project.helpmeat.constant.Constants
import com.project.helpmeat.utils.AnimationUtils

class MeatLayoutController(
    private val mContext: Context,
    private var mGrillSettingsDataController: GrillSettingsDataController,
    view: View,
    okayButtonCallBack: OkayButtonCallBack
) : LayoutController(okayButtonCallBack) {

    private lateinit var mMeatLayout: ConstraintLayout
    private lateinit var mMeatLayoutTopContainer: LinearLayout // Fork
    private lateinit var mMeatLayoutBottomContainer: LinearLayout // Beef
    private lateinit var mMeatDetailLayout: LinearLayout
    private lateinit var mMeatDetailBackButton: TextView
    private lateinit var mDetailMeatList: RecyclerView
    private lateinit var mDetailMeatListAdapter: MeatListAdapter

    init {
        initLayout(view)
    }

    override fun initLayout(view: View) {
        mMeatLayout = view.findViewById(R.id.layout_grill_settings_meat)

        mMeatLayoutTopContainer = view.findViewById(R.id.layout_grill_settings_meat_top_container)
        mMeatLayoutTopContainer.setOnClickListener {
            mDetailMeatListAdapter.updateMeatType(Constants.MeatType.MEAT_TYPE_FORK)
            AnimationUtils.playMoveLeftAnimation(mContext, mMeatLayout, mMeatDetailLayout)
            showOKButton()
        }

        mMeatLayoutBottomContainer = view.findViewById(R.id.layout_grill_settings_meat_bottom_container)
        mMeatLayoutBottomContainer.setOnClickListener {
            mDetailMeatListAdapter.updateMeatType(Constants.MeatType.MEAT_TYPE_BEEF)
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

    override fun display() {
        AnimationUtils.playFullScaleUpAnimation(mContext, mMeatLayout)
    }

    override fun complete() {
        mGrillSettingsDataController.notifyMeatCompleted()

        AnimationUtils.playFullScaleDownAnimation(mContext, mMeatDetailLayout)
        hideOKButton()
    }
}