package com.project.helpmeat.view.base

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import com.project.helpmeat.R
import com.project.helpmeat.navigator.AppNavigator
import com.project.helpmeat.utils.ViewUtils
import com.project.helpmeat.view.MainActivity
import com.project.helpmeat.viewmodel.AppViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {
    protected val TAG = this.javaClass.simpleName

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main

    private var mToolbar: Toolbar? = null

    @Inject
    lateinit var mNavigator: AppNavigator

    protected val mAppViewModel: AppViewModel by viewModels()

    companion object {
        const val INVALID_ID = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mJob = SupervisorJob()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mToolbar = view.findViewById(R.id.app_toolbar)
        mToolbar?.let {
            val mainActivity = activity as MainActivity
            mainActivity.setSupportActionBar(it)
            mainActivity.supportActionBar?.setHomeAsUpIndicator(getMenuHomeIconId())
            mainActivity.supportActionBar?.setDisplayShowTitleEnabled(true)
            mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            mainActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

            requireActivity().addMenuProvider(object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(getMenuId(), menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return this@BaseFragment.onMenuItemSelected(menuItem)
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        }
    }

    override fun onDestroy() {
        mJob.cancel()
        super.onDestroy()
    }

    /**
     * Keyboard
     */
    fun showKeyboard(view: View) {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, 0)
    }

    fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    // If this method is overriding, super.onViewCreated method should also be called
    fun setActionBarTitle(title: String, color: Int) {
        mToolbar?.let {
            it.setTitleTextColor(color)
            it.title = title
        }
    }

    open fun getMenuId(): Int = INVALID_ID

    open fun getMenuHomeIconId(): Int = R.drawable.ic_back

    open fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                mNavigator.back()
                true
            }
            else -> false
        }
    }

    fun limitContentViewArea(view: View, root: Boolean) {
        if (root) {
            view.setPadding(
                view.paddingLeft,
                view.paddingTop + ViewUtils.getStatusBarHeight(view.context),
                view.paddingRight,
                view.paddingBottom + ViewUtils.getStatusBarHeight(view.context)
            )
        } else {
            ViewUtils.setMarginTopBottom(
                view,
                view.marginTop + ViewUtils.getStatusBarHeight(view.context),
                view.marginBottom + ViewUtils.getNavigationBarHeight(view.context)
            )
        }
    }
}