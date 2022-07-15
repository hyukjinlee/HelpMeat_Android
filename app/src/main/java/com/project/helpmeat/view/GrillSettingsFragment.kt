package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.project.helpmeat.R
import com.project.helpmeat.constant.Constants
import com.project.helpmeat.controller.*
import com.project.helpmeat.utils.AnimationUtils
import com.project.helpmeat.utils.ResourceUtils
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class GrillSettingsFragment : BaseFragment(), GrillSettingsDataObserver, OkayButtonCallBack {
    companion object {
        const val ALPHA_SHOW = 1.0F
        const val ALPHA_TRANSPARENT = 0.5F
        const val ALPHA_HIDE = 0.0F

        const val BLINK_ANIMATION_DURATION = 500L
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

    private fun toStep(index: Int): Step? {
        return when (index) {
            Step.MEAT.index() -> Step.MEAT
            Step.WIDTH.index() -> Step.WIDTH
            Step.GRILL.index() -> Step.GRILL
            Step.DEGREE.index() -> Step.DEGREE
            Step.FINISH.index() -> Step.FINISH
            else -> null
        }
    }

    private val mTextList = ArrayList<TextView>()

    private lateinit var mMeatImage: ImageView
    private val mSettingButtonList = ArrayList<TextView>()
    private lateinit var mOKButton: Button

    private var mCurrentStep = Step.MEAT
    private var mLastStep = Step.MEAT

    @Inject
    lateinit var mGrillSettingsDataController: GrillSettingsDataController
    private val mLayoutController = ArrayList<LayoutControllable>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_grill_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = view.findViewById<ConstraintLayout>(R.id.fragment_grill_settings_content_container)
        limitContentViewArea(root, false)

        initGrillSettingsControllers(view)
        initSettingMainComponents(view)

        moveToNext()
    }

    override fun onDestroyView() {
        mGrillSettingsDataController.removeObserverAll()

        super.onDestroyView()
    }

    private fun initGrillSettingsControllers(view: View) {
        mGrillSettingsDataController.addObserver(this)
        mLayoutController.add(MeatLayoutController(requireContext(), mGrillSettingsDataController, view, this))
        mLayoutController.add(WidthLayoutController(requireContext(), mGrillSettingsDataController, view, this))
    }

    private fun initSettingMainComponents(view: View) {
        mTextList.add(view.findViewById(R.id.fragment_grill_settings_text_first))
        mTextList.add(view.findViewById(R.id.fragment_grill_settings_text_second))
        mTextList.add(view.findViewById(R.id.fragment_grill_settings_text_third))

        mMeatImage = view.findViewById(R.id.fragment_grill_settings_meat_image)

        mSettingButtonList.add(view.findViewById(R.id.fragment_grill_settings_meat_button))
        mSettingButtonList.add(view.findViewById(R.id.fragment_grill_settings_width_button))
        mSettingButtonList.add(view.findViewById(R.id.fragment_grill_settings_grill_button))
        mSettingButtonList.add(view.findViewById(R.id.fragment_grill_settings_degree_button))

        for (i in Step.MEAT.index()..Step.DEGREE.index()) {
            mSettingButtonList[i].setOnTouchListener(mOnTouchListener)
            mSettingButtonList[i].setOnClickListener {
                if (it.isClickable) {
                    try {
                        mCurrentStep = toStep(i)!!
                        mLayoutController[i].display()
                    } catch (e: IndexOutOfBoundsException) {

                    }
                }
            }
            mSettingButtonList[i].isClickable = false
        }

        mOKButton = view.findViewById(R.id.fragment_grill_settings_ok_button)
        mOKButton.setOnClickListener {
            try {
                mLayoutController[mCurrentStep.index()].select()
            } catch (e: IndexOutOfBoundsException) {

            }
        }
    }

    private fun moveToNext() {
        try {
            mLayoutController[mLastStep.index()].prepare()
        } catch (e: IndexOutOfBoundsException) {

        }
        mSettingButtonList[mLastStep.index()].isClickable = true

        val textArray = getDescription(mLastStep)

        for (i in textArray.indices) {
            if (textArray[i].isEmpty()) {
                mTextList[i].visibility = View.GONE
            } else {
                mTextList[i].visibility = View.VISIBLE
                mTextList[i].text = textArray[i]
            }
        }

        val context = requireContext()

        if (mLastStep.index() < Step.FINISH.index()) {
            with(mSettingButtonList[mLastStep.index()]) {
                AnimationUtils.playBlinkAnimation(BLINK_ANIMATION_DURATION, this)
                background = context.getDrawable(R.drawable.bg_rounded_rectangle_50_pink)
                setTextColor(context.getColor(R.color.white))
            }
        }
    }

    private fun onStepCompleted() {
        if (mCurrentStep.index() == mLastStep.index()) {
            mSettingButtonList[mLastStep.index()].clearAnimation()

            mLastStep.next()?.let {
                mLastStep = it
            }
            moveToNext()
        }
    }


    private fun getDescription(step: Step): Array<String> {
        return when (step) {
            Step.MEAT -> {
                ResourceUtils.getMeatSettingDescription(requireContext())
            }
            Step.WIDTH -> {
                ResourceUtils.getWidthSettingDescription(requireContext())
            }
            Step.GRILL -> {
                ResourceUtils.getGrillSettingDescription(requireContext())
            }
            Step.DEGREE -> {
                ResourceUtils.getDegreeSettingDescription(requireContext())
            }
            Step.FINISH -> {
                ResourceUtils.getFinishDescription(requireContext())
            }
        }
    }

    override fun onMeatSelected(meatValue: Int) {
        when (Constants.getMeatType(meatValue)) {
            Constants.MeatType.MEAT_TYPE_FORK -> {
                mMeatImage.setImageDrawable(requireContext().getDrawable(R.drawable.fork_with_shadow))
            }
            Constants.MeatType.MEAT_TYPE_BEEF -> {
                mMeatImage.setImageDrawable(requireContext().getDrawable(R.drawable.beef_with_shadow))
            }
            Constants.MeatType.MEAT_TYPE_ERROR -> {}
        }

        with(mSettingButtonList[Step.MEAT.index()]) {
            text = ResourceUtils.getMeatName(requireContext(), meatValue)
            clearAnimation()
            background = context.getDrawable(R.drawable.bg_rounded_rectangle_50_pink)
            setTextColor(context.getColor(R.color.white))
        }
        onStepCompleted()
    }

    override fun onWidthSelected(width: Float) {
        mSettingButtonList[Step.WIDTH.index()].text = "$width cm"
        onStepCompleted()
    }

    override fun onGrillSelected() {
        mSettingButtonList[Step.GRILL.index()].text = ""
        onStepCompleted()
    }

    override fun onDegreeSelected() {
        mSettingButtonList[Step.DEGREE.index()].text = ""
        onStepCompleted()
    }

    override fun showOKButton() {
        mOKButton.visibility = View.VISIBLE
    }

    override fun hideOKButton() {
        mOKButton.visibility = View.GONE
    }

    override fun needTouchAnimation() = true
}

interface OkayButtonCallBack {
    fun showOKButton()

    fun hideOKButton()
}