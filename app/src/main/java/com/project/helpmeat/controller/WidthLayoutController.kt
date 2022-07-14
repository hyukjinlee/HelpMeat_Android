package com.project.helpmeat.controller

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.project.helpmeat.R
import com.project.helpmeat.utils.AnimationUtils
import com.project.helpmeat.view.OkayButtonCallBack
import kotlin.math.roundToInt

class WidthLayoutController(
    context: Context,
    grillSettingsDataController: GrillSettingsDataController,
    view: View,
    okayButtonCallBack: OkayButtonCallBack
) : LayoutControllable(context, grillSettingsDataController, okayButtonCallBack) {

    companion object {
        private const val FIRST_SIGN_VALUE = 0.5f
        private const val MM_TO_CM = 10.0f
        private const val SIGN_HALF_HEIGHT = 65.0f
        private const val Y_FIGURE_PER_MM = 17.0f
        private const val MAX_COUNT = 9
        private const val FIRST_MARKING_Y = 95.0f
        private const val LAST_MARKING_Y = FIRST_MARKING_Y + (Y_FIGURE_PER_MM * 5 * (MAX_COUNT - 1))
    }

    private lateinit var mWidthLayout: RelativeLayout
    private lateinit var mSign: TextView
    private lateinit var mRuler: ImageView

    private var mTouchStartX = 0.0f
    private var mTouchEndX = 0.0f
    private var mTouchStartY = 0.0f
    private var mTouchEndY = 0.0f

    private var mSignValue = 1.0f

    init {
        initLayout(view)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun initLayout(view: View) {
        mWidthLayout = view.findViewById(R.id.layout_grill_settings_width)
        mWidthLayout.setOnTouchListener { v, e ->
            when (e.action) {
                MotionEvent.ACTION_DOWN,
                MotionEvent.ACTION_MOVE -> {
                    if (e.x in mTouchStartX..mTouchEndX &&
                        e.y in mTouchStartY..mTouchEndY) {
                        moveSign(e.y)
                    }
                }
            }
            v?.onTouchEvent(e) ?: true
        }
        mSign = view.findViewById(R.id.layout_grill_settings_width_sign)
        mRuler = view.findViewById(R.id.layout_grill_settings_width_ruler)
    }

    override fun prepare() {
        mWidthLayout.visibility = View.INVISIBLE
        mWidthLayout.post {
            calculateRulerRange()
        }
    }

    override fun display() {
        AnimationUtils.playFullScaleUpAnimation(mContext, mWidthLayout)
        showOKButton()
    }

    override fun select() {
        mGrillSettingsDataController.notifyWidthSelected(mSignValue)

        AnimationUtils.playFullScaleDownAnimation(mContext, mWidthLayout)
        hideOKButton()
    }

    private fun calculateRulerRange() {
        mTouchStartX = mSign.left.toFloat()
        mTouchEndX = mRuler.right.toFloat()
        mTouchStartY = mRuler.top.toFloat()
        mTouchEndY = mRuler.bottom.toFloat()
    }

    private fun moveSign(y: Float) {
        var signY = y - SIGN_HALF_HEIGHT

        if (signY < FIRST_MARKING_Y) {
            signY = FIRST_MARKING_Y
        } else if (signY > LAST_MARKING_Y) {
            signY = LAST_MARKING_Y
        }

        mSignValue = FIRST_SIGN_VALUE + (((signY - FIRST_MARKING_Y) / Y_FIGURE_PER_MM) / MM_TO_CM)
        mSignValue = (mSignValue * 10).roundToInt() / 10.0f

        mSign.y = signY
        mSign.text = mContext.resources.getString(R.string.layout_grill_settings_width_ruler_text, mSignValue)
    }
}