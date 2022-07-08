package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import com.project.helpmeat.R
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GrillSettingsFragment : BaseFragment() {
    companion object {
        const val ALPHA_SHOW = 1.0F
        const val ALPHA_HIDE = 0.0F
    }

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
    }
}