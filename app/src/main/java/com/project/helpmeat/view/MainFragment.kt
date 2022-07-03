package com.project.helpmeat.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.project.helpmeat.R
import com.project.helpmeat.view.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = view.findViewById<FrameLayout>(R.id.fragment_main_content_container)

        limitContentViewArea(root, false)

        setActionBarTitle("Help Meat", requireContext().getColor(R.color.heavy_pink))
    }

    override fun getMenuId(): Int = R.menu.main_fragment_menu

    override fun getMenuHomeIconId(): Int = R.drawable.ic_home

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                // Do nothing (TBD)
                true
            }
            else -> super.onMenuItemSelected(menuItem)
        }
    }
}