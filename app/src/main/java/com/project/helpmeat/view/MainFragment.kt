package com.project.helpmeat.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.helpmeat.R
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private lateinit var mTopContainer: ConstraintLayout
    private lateinit var mMiddleLeftContainer: LinearLayout
    private lateinit var mMiddleRightContainer: LinearLayout
    private lateinit var mBottomContainer: FrameLayout

    private lateinit var mScaleUpAnimation: Animation
    private lateinit var mScaleDownAnimation: Animation

    @SuppressLint("ClickableViewAccessibility")
    private val mOnTouchListener = View.OnTouchListener { v, e ->
        if (v != null && e != null) {
            when (e.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!mIsItemClicked) {
                        mIsItemClicked = true
                        v.startAnimation(mScaleDownAnimation)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (mIsItemClicked) {
                        mIsItemClicked = false
                        v.clearAnimation()
                    }
                }
            }
        }
        v?.onTouchEvent(e) ?: true
    }

    private var mIsItemClicked = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = view.findViewById<LinearLayout>(R.id.fragment_main_content_container)
        limitContentViewArea(root, false)

        setActionBarTitle("Help Meat", requireContext().getColor(R.color.heavy_pink))

        mScaleUpAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_up_small)
        mScaleUpAnimation.fillAfter = true
        mScaleDownAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_down_small)
        mScaleDownAnimation.fillAfter = true

        mTopContainer = view.findViewById(R.id.fragment_main_top_container)
        mTopContainer.setOnTouchListener(mOnTouchListener)
        mMiddleLeftContainer = view.findViewById(R.id.fragment_main_middle_left_container)
        mMiddleLeftContainer.setOnTouchListener(mOnTouchListener)
        mMiddleRightContainer = view.findViewById(R.id.fragment_main_middle_right_container)
        mMiddleRightContainer.setOnTouchListener(mOnTouchListener)
        mBottomContainer = view.findViewById(R.id.fragment_main_bottom_container)
        mBottomContainer.setOnTouchListener(mOnTouchListener)
    }

    override fun getMenuId(): Int = R.menu.main_fragment_menu

    override fun getMenuHomeIconId(): Int = R.drawable.ic_home

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                // Do nothing (TBD)
                true
            }
            else -> super.onMenuItemSelected(menuItem)
        }
    }
}