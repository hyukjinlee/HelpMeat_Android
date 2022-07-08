package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.helpmeat.R
import com.project.helpmeat.navigator.Anim
import com.project.helpmeat.navigator.AppScreens
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private lateinit var mTopContainer: ConstraintLayout
    private lateinit var mMiddleLeftContainer: LinearLayout
    private lateinit var mMiddleRightContainer: LinearLayout
    private lateinit var mBottomContainer: FrameLayout

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

        setActionBarTitle("Help Meat")

        mTopContainer = view.findViewById(R.id.fragment_main_top_container)
        mTopContainer.setOnTouchListener(mOnTouchListener)
        mTopContainer.setOnClickListener {
            mNavigator.navigateTo(AppScreens.GRILL_SETTINGS, Anim.SLIDE)
        }
        mMiddleLeftContainer = view.findViewById(R.id.fragment_main_middle_left_container)
        mMiddleLeftContainer.setOnTouchListener(mOnTouchListener)
        mMiddleRightContainer = view.findViewById(R.id.fragment_main_middle_right_container)
        mMiddleRightContainer.setOnTouchListener(mOnTouchListener)
        mBottomContainer = view.findViewById(R.id.fragment_main_bottom_container)
        mBottomContainer.setOnTouchListener(mOnTouchListener)
    }

    override fun needTouchAnimation() = true

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