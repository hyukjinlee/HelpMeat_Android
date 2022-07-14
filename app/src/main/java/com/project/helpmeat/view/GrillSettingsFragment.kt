package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.project.helpmeat.R
import com.project.helpmeat.constant.Constants
import com.project.helpmeat.controller.GrillSettingsDataController
import com.project.helpmeat.controller.GrillSettingsDataObserver
import com.project.helpmeat.controller.LayoutControllable
import com.project.helpmeat.controller.MeatLayoutController
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

    private val mTextList = ArrayList<TextView>()

    private lateinit var mMeatImage: ImageView
    private lateinit var mMeatButton: TextView
    private lateinit var mWidthButton: TextView
    private lateinit var mGrillButton: TextView
    private lateinit var mDegreeButton: TextView
    private lateinit var mOKButton: Button

    private var mCurrentStep = Step.MEAT

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

        val root = view.findViewById<RelativeLayout>(R.id.fragment_grill_settings_content_container)
        limitContentViewArea(root, false)

        initGrillSettingsControllers(view)
        initSettingMainComponents(view)
        playCurrentStep()
    }

    override fun onDestroyView() {
        mGrillSettingsDataController.removeObserverAll()

        super.onDestroyView()
    }

    private fun initGrillSettingsControllers(view: View) {
        mGrillSettingsDataController.addObserver(this)
        mLayoutController.add(MeatLayoutController(requireContext(), mGrillSettingsDataController, view, this))
    }

    private fun initSettingMainComponents(view: View) {
        mTextList.add(view.findViewById(R.id.fragment_grill_settings_text_first))
        mTextList.add(view.findViewById(R.id.fragment_grill_settings_text_second))
        mTextList.add(view.findViewById(R.id.fragment_grill_settings_text_third))

        mMeatImage = view.findViewById(R.id.fragment_grill_settings_meat_image)

        mMeatButton = view.findViewById(R.id.fragment_grill_settings_meat_button)
        mMeatButton.setOnTouchListener(mOnTouchListener)
        mMeatButton.setOnClickListener {
            mLayoutController[Step.MEAT.index()].display()
        }

        mWidthButton = view.findViewById(R.id.fragment_grill_settings_width_button)
        mWidthButton.setOnTouchListener(mOnTouchListener)

        mGrillButton = view.findViewById(R.id.fragment_grill_settings_grill_button)
        mGrillButton.setOnTouchListener(mOnTouchListener)

        mDegreeButton = view.findViewById(R.id.fragment_grill_settings_state_button)
        mDegreeButton.setOnTouchListener(mOnTouchListener)

        mOKButton = view.findViewById(R.id.fragment_grill_settings_ok_button)
        mOKButton.setOnClickListener {
            mLayoutController[mCurrentStep.index()].complete()
        }
    }

    private fun playCurrentStep() {
        val textArray = getDescription(mCurrentStep)

        for (i in textArray.indices) {
            if (textArray[i].isEmpty()) {
                mTextList[i].visibility = View.GONE
            } else {
                mTextList[i].visibility = View.VISIBLE
                mTextList[i].text = textArray[i]
            }
        }

        val context = requireContext()
        getButton(mCurrentStep)?.let {
            AnimationUtils.playBlinkAnimation(BLINK_ANIMATION_DURATION, it)
            it.background = context.getDrawable(R.drawable.bg_rounded_rectangle_150_pink)
            it.setTextColor(context.getColor(R.color.white))
        }
    }

    private fun onStepCompleted(completedStep: Step) {
        if (completedStep.index() >= mCurrentStep.index()) {
            mCurrentStep.next()?.let {
                mCurrentStep = it
            }
            getButton(completedStep)?.clearAnimation()
            playCurrentStep()
        }
    }

    private fun getButton(step: Step): TextView? {
        return when (step) {
            Step.MEAT -> {
                mMeatButton
            }
            Step.WIDTH -> {
                mWidthButton
            }
            Step.GRILL -> {
                mGrillButton
            }
            Step.DEGREE -> {
                mDegreeButton
            }
            Step.FINISH -> {
                null
            }
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

        with(mMeatButton) {
            text = ResourceUtils.getMeatName(requireContext(), meatValue)
            clearAnimation()
            background = context.getDrawable(R.drawable.bg_rounded_rectangle_150_pink)
            setTextColor(context.getColor(R.color.white))
        }
        onStepCompleted(Step.MEAT)
    }

    override fun onWidthSelected() {
        mWidthButton.text = ""
        onStepCompleted(Step.WIDTH)
    }

    override fun onGrillSelected() {
        mGrillButton.text = ""
        onStepCompleted(Step.GRILL)
    }

    override fun onDegreeSelected() {
        mDegreeButton.text = ""
        onStepCompleted(Step.DEGREE)
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