package com.project.helpmeat.controller

import android.content.Context
import android.view.View
import com.project.helpmeat.view.OkayButtonCallBack

abstract class LayoutControllable(
    protected val mContext: Context,
    protected var mGrillSettingsDataController: GrillSettingsDataController,
    private val mOkayButtonCallBack: OkayButtonCallBack
) {
    abstract fun initLayout(view: View)

    open fun prepare() {}

    abstract fun display()

    abstract fun select()

    protected fun showOKButton() {
        mOkayButtonCallBack.showOKButton()
    }

    protected fun hideOKButton() {
        mOkayButtonCallBack.hideOKButton()
    }
}