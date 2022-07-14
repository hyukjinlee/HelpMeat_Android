package com.project.helpmeat.controller

import android.view.View
import com.project.helpmeat.view.OkayButtonCallBack

abstract class LayoutControllable(private val mOkayButtonCallBack: OkayButtonCallBack) {
    abstract fun initLayout(view: View)

    abstract fun display()

    abstract fun complete()

    protected fun showOKButton() {
        mOkayButtonCallBack.showOKButton()
    }

    protected fun hideOKButton() {
        mOkayButtonCallBack.hideOKButton()
    }
}