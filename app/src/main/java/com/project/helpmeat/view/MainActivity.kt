package com.project.helpmeat.view

import android.os.Bundle
import com.project.helpmeat.R
import com.project.helpmeat.navigator.Anim
import com.project.helpmeat.navigator.AppNavigator
import com.project.helpmeat.navigator.AppScreens
import com.project.helpmeat.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var mNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            mNavigator.navigateTo(AppScreens.INIT, Anim.FADE)
        }
    }
}