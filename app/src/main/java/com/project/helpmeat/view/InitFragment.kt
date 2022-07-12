package com.project.helpmeat.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doOnTextChanged
import com.project.helpmeat.R
import com.project.helpmeat.navigator.Anim
import com.project.helpmeat.navigator.AppScreens
import com.project.helpmeat.repository.db.UserInfo
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitFragment : BaseFragment() {
    private lateinit var mInput: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_init, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val startButton = view.findViewById<ImageButton>(R.id.fragment_init_button)
        mInput = view.findViewById(R.id.fragment_init_input_id)
        mInput.doOnTextChanged { text, _, _, _ ->
            if (text.isNullOrBlank()) {
                startButton.isClickable = false
                startButton.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.black_fire))
            } else {
                startButton.isClickable = true
                startButton.setImageDrawable(AppCompatResources.getDrawable(view.context, R.drawable.fire_with_message_init))
            }
        }

        startButton.setOnTouchListener (mOnTouchListener)
        startButton.setOnClickListener {
            mAppViewModel.updateUserInfo(UserInfo(0, mInput.text.toString()))
            mNavigator.navigateTo(AppScreens.MAIN, Anim.SLIDE)
        }

        observeDB()
    }

    private fun observeDB() {
        mAppViewModel.mUserInfo.observe(viewLifecycleOwner) { list ->
            list?.let {
                if (it.isNotEmpty()) {
                    mAppViewModel.mUserInfo.removeObservers(viewLifecycleOwner)
                    mInput.setText(it[0].mUserName)
                }
            }
        }
    }

    override fun needTouchAnimation() = true
}