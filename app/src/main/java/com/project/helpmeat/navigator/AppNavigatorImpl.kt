package com.project.helpmeat.navigator

import androidx.fragment.app.FragmentActivity
import com.project.helpmeat.R
import com.project.helpmeat.view.IntroFragment
import com.project.helpmeat.view.MainFragment
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val mActivity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: AppScreens) {
        val fragment = when (screen) {
            AppScreens.INTRO -> IntroFragment()
            AppScreens.MAIN -> MainFragment()
        }

        if (!mActivity.supportFragmentManager.isDestroyed) {
            mActivity.supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
//                .addToBackStack(fragment::class.java.canonicalName)
                .replace(R.id.main_container, fragment)
                .commit()
        }
    }
}