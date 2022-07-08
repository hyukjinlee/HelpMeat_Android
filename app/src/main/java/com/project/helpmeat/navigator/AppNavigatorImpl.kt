package com.project.helpmeat.navigator

import androidx.fragment.app.FragmentActivity
import com.project.helpmeat.R
import com.project.helpmeat.view.GrillSettingsFragment
import com.project.helpmeat.view.InitFragment
import com.project.helpmeat.view.IntroFragment
import com.project.helpmeat.view.MainFragment
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val mActivity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: AppScreens, anim: Anim) {
        val needStack: Boolean
        val fragment = when (screen) {
            AppScreens.INTRO -> {
                needStack = false
                IntroFragment()
            }
            AppScreens.INIT -> {
                needStack = false
                InitFragment()
            }
            AppScreens.MAIN -> {
                needStack = false
                MainFragment()
            }
            AppScreens.GRILL_SETTINGS -> {
                needStack = true
                GrillSettingsFragment()
            }
        }

        if (!mActivity.supportFragmentManager.isDestroyed) {
            val transaction = mActivity.supportFragmentManager.beginTransaction()

            when (anim) {
                Anim.FADE -> transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                Anim.SLIDE -> transaction.setCustomAnimations(
                    R.anim.enter_from_right,
                    R.anim.exit_to_left,
                    R.anim.enter_from_left,
                    R.anim.exit_to_right
                )
            }

            transaction.replace(R.id.main_container, fragment)
            if (needStack) {
                transaction.addToBackStack(fragment::class.java.canonicalName)
            }
            transaction.commit()
        }
    }

    override fun back() {
        if (!mActivity.supportFragmentManager.isDestroyed) {
            val count = mActivity.supportFragmentManager.backStackEntryCount
            if (count > 0) {
                val currentFragment = mActivity.supportFragmentManager.fragments[count - 1]
                mActivity.supportFragmentManager.beginTransaction().remove(currentFragment)
            } else {
                mActivity.finish()
            }
        }
    }
}