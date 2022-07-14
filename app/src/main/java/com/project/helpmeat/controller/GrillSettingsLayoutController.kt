package com.project.helpmeat.controller

import android.content.Context
import android.view.View
import android.widget.Button
import com.project.helpmeat.R

class GrillSettingsLayoutController(
    private val mContext: Context,
    private var mGrillSettingsDataController: GrillSettingsDataController,
    view: View,
) : OkayButtonCallBack {
    companion object {
        const val TAG = "GrillSettingsLayoutController"
    }

    enum class Step {
        MEAT {
            override fun index() = 0
            override fun next() = WIDTH
        },
        WIDTH {
            override fun index() = 1
            override fun next() = GRILL
        },
        GRILL {
            override fun index() = 2
            override fun next() = DEGREE
        },
        DEGREE {
            override fun index() = 3
            override fun next() = FINISH
        },
        FINISH {
            override fun index() = 4
            override fun next(): Step? = null
        };

        abstract fun index(): Int
        abstract fun next(): Step?
    }

    private lateinit var mCurrentStep: Step
    private lateinit var mOKButton: Button

    private val mLayoutController = ArrayList<LayoutController>()

    init {
        initOKButton(view)
        initLayoutController(view)
    }

    private fun initOKButton(view: View) {
        mOKButton = view.findViewById(R.id.fragment_grill_settings_ok_button)
        mOKButton.setOnClickListener {
            mLayoutController[mCurrentStep.index()].complete()
        }
    }

    private fun initLayoutController(view: View) {
        mLayoutController.add(MeatLayoutController(mContext, mGrillSettingsDataController, view, this))
    }

    fun display(step: Step) {
        mCurrentStep = step
        mLayoutController[mCurrentStep.index()].display()
    }

    override fun showOKButton() {
        mOKButton.visibility = View.VISIBLE
    }

    override fun hideOKButton() {
        mOKButton.visibility = View.GONE
    }
}

interface OkayButtonCallBack {
    fun showOKButton()

    fun hideOKButton()
}