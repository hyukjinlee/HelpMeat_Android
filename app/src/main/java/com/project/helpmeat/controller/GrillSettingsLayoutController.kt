package com.project.helpmeat.controller

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.project.helpmeat.R
import com.project.helpmeat.components.MeatListAdapter
import com.project.helpmeat.constant.Constants.Companion.MEAT_TYPE_BEEF
import com.project.helpmeat.constant.Constants.Companion.MEAT_TYPE_FORK
import com.project.helpmeat.utils.ResourceUtils
import java.util.function.Consumer

class GrillSettingsLayoutController(private val mContext: Context, view: View) {

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

    private val mFullScaleUpAnimation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_up_full)
    private val mFullScaleDownAnimation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.scale_down_full)
    private val mExitToLeftDownAnimation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.exit_to_left)
    private val mEnterFromRightDownAnimation: Animation =
        AnimationUtils.loadAnimation(mContext, R.anim.enter_from_right)
    private val mExitToRightDownAnimation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.exit_to_right)
    private val mEnterFromLeftDownAnimation: Animation = AnimationUtils.loadAnimation(mContext, R.anim.enter_from_left)

    private lateinit var mOnMeatSelectedListener: Consumer<Int>
    private lateinit var mOnWidthSelectedListener: Consumer<Int>
    private lateinit var mOnGrillSelectedListener: Consumer<Int>
    private lateinit var mOnStateSelectedListener: Consumer<Int>

    private lateinit var mCurrentStep: Step
    private var mMeatType = 0

    init {
        mMainLayout = view.findViewById(R.id.fragment_grill_settings_main)

        initMeatLayout(view)
    }

    fun setOnSelectedListeners(
        meatListener: Consumer<Int>, widthListener: Consumer<Int>,
        grillListener: Consumer<Int>, stateListener: Consumer<Int>
    ) {
        mOnMeatSelectedListener = meatListener
        mOnWidthSelectedListener = widthListener
        mOnGrillSelectedListener = grillListener
        mOnStateSelectedListener = stateListener
    }

    private fun initMeatLayout(view: View) {
        mMeatLayout = view.findViewById(R.id.fragment_grill_settings_meat)

        mMeatLayoutTopContainer = view.findViewById(R.id.fragment_grill_settings_meat_top_container)
        mMeatLayoutTopContainer.setOnClickListener {
            mMeatType = MEAT_TYPE_FORK
            mDetailMeatListAdapter.updateMeatList(ResourceUtils.getForkList(mContext))
            playMoveLeftAnimation(mMeatLayout, mMeatDetailLayout)
        }

        mMeatLayoutBottomContainer = view.findViewById(R.id.fragment_grill_settings_meat_bottom_container)
        mMeatLayoutBottomContainer.setOnClickListener {
            mMeatType = MEAT_TYPE_BEEF
            mDetailMeatListAdapter.updateMeatList(ResourceUtils.getBeefList(mContext))
            playMoveLeftAnimation(mMeatLayout, mMeatDetailLayout)
        }

        mMeatDetailLayout = view.findViewById(R.id.fragment_grill_settings_meat_detail)
        mMeatDetailBackButton = view.findViewById(R.id.fragment_grill_settings_meat_detail_back_button)
        mMeatDetailBackButton.setOnClickListener {
            playMoveRightAnimation(mMeatDetailLayout, mMeatLayout)
        }

        mDetailMeatList = view.findViewById(R.id.fragment_grill_settings_meat_detail_list)
        mDetailMeatListAdapter = MeatListAdapter()
        mDetailMeatListAdapter.setOnSelectedListener { selectedMeatType ->
            mMeatType += selectedMeatType
            mOnMeatSelectedListener.accept(mMeatType)
            hideLayout(mMeatDetailLayout)
        }
        mDetailMeatList.adapter = mDetailMeatListAdapter

    }

    private fun playMoveLeftAnimation(exitView: View, enterView: View) {
        exitView.visibility = View.VISIBLE
        setGoneListener(exitView, mExitToLeftDownAnimation)
        exitView.startAnimation(mExitToLeftDownAnimation)

        enterView.visibility = View.VISIBLE
        enterView.startAnimation(mEnterFromRightDownAnimation)
    }

    private fun playMoveRightAnimation(exitView: View, enterView: View) {
        exitView.visibility = View.VISIBLE
        setGoneListener(exitView, mExitToRightDownAnimation)
        exitView.startAnimation(mExitToRightDownAnimation)

        enterView.visibility = View.VISIBLE
        enterView.startAnimation(mEnterFromLeftDownAnimation)
    }

    private fun setGoneListener(view: View, animation: Animation) {
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

    fun showLayout(step: Step) {
        mCurrentStep = step
        val layout = when (step) {
            Step.MEAT -> {
                mMeatLayout
            }
            Step.WIDTH -> {
                mMeatLayout
            }
            Step.GRILL -> {
                mMeatLayout
            }
            Step.STATE -> {
                mMeatLayout
            }
        }
        layout.visibility = View.VISIBLE
        layout.startAnimation(mFullScaleUpAnimation)
    }

    private fun hideLayout(layout: View) {
        layout.visibility = View.VISIBLE
        setGoneListener(layout, mFullScaleDownAnimation)
        layout.startAnimation(mFullScaleDownAnimation)
    }
}