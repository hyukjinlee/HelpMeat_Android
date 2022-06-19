package com.project.helpmeat.navigator

import androidx.fragment.app.FragmentActivity
import com.project.helpmeat.R
import com.project.helpmeat.view.InitFragment
import com.project.helpmeat.view.IntroFragment
import com.project.helpmeat.view.MainFragment
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val mActivity: FragmentActivity) : AppNavigator {
    override fun navigateTo(screen: AppScreens, anim: Anim) {
        val fragment = when (screen) {
            AppScreens.INTRO -> IntroFragment()
            AppScreens.INIT -> InitFragment()
            AppScreens.MAIN -> MainFragment()
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

            transaction.replace(R.id.main_container, fragment).commit()
//          .addToBackStack(fragment::class.java.canonicalName)
        }
    }
}