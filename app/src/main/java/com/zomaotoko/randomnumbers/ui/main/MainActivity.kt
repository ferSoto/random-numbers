package com.zomaotoko.randomnumbers.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.DisplayMetrics
import android.view.View
import com.zomaotoko.randomnumbers.R
import kotlinx.android.synthetic.main.activity_main.*

import com.zomaotoko.randomnumbers.data.enums.NumberType
import com.zomaotoko.randomnumbers.data.local.GeneratorSettings
import com.zomaotoko.randomnumbers.data.local.LocalStorage
import com.zomaotoko.randomnumbers.utils.Utils.Companion.dpToPx
import com.zomaotoko.randomnumbers.ui.menu.MenuAdapter
import com.zomaotoko.randomnumbers.ui.config.ConfigurationFragment

class MainActivity : FragmentActivity(), ConfigurationFragment.ConfigurationSelector, MenuAdapter.MenuListener {
    private lateinit var localStorage: LocalStorage
    private lateinit var numberType: NumberType
    private var lowerBound: Float = 0f
    private var upperBound: Float = 0f
    private var digits: Int = 1
    private lateinit var generatorFragment: GenerateNumberFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inflateLayout()
        loadConfiguration()
        adjustMenuWidth()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(menuLayout)) {
            onCloseDrawer()
        } else {
            super.onBackPressed()
        }
    }

    override fun onTypeSelected(type: NumberType) {
        if (numberType == type) return
        generatorFragment.setDigits(digits)
        localStorage.saveNumberType(type)
        generatorFragment.setNumberType(type)
    }

    override fun onBoundariesSelected(lowerBound: Float, upperBound: Float) {
        this.lowerBound = lowerBound
        this.upperBound = upperBound
        generatorFragment.setBoundaries(lowerBound, upperBound)
        with(localStorage) {
            saveLowerBound(lowerBound)
            saveUpperBound(upperBound)
        }
    }

    override fun onDigitsSelected(digits: Int) {
        this.digits = digits
        generatorFragment.setDigits(digits)
        localStorage.saveDigits(digits)
    }

    override fun onHomeClick() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            // Remove visible fragment if it's not the main fragment
            supportFragmentManager.popBackStackImmediate()
        }
        onCloseDrawer()
    }

    override fun onConfigurationClick() {
        if (supportFragmentManager.findFragmentByTag(CONFIGURATION_TAG) == null) {
            showConfigurationFragment()
        }
    }

    fun onTitleBarButtonClick(view: View? = null) {
        drawerLayout.openDrawer(menuLayout)
    }

    fun onCloseDrawer(view: View? = null) {
        drawerLayout.closeDrawer(menuLayout)
    }

    private fun inflateLayout() {
        generatorFragment = fragmentManager.findFragmentById(R.id.fragment_generate_number) as GenerateNumberFragment
    }

    private fun loadConfiguration() {
        localStorage = GeneratorSettings(this).apply {
            numberType = retrieveNumberType()
            digits = retrieveDigits()
            lowerBound = retrieveLowerBound()
            upperBound = retrieveUpperBound()
        }

        generatorFragment.setNumberType(numberType)
        generatorFragment.setDigits(digits)
        generatorFragment.setBoundaries(lowerBound, upperBound)
    }

    private fun adjustMenuWidth() {
        // Menu layout must be 48 dp smaller than screen width
        menuLayoutWidth = screenWidthInDp - dpToPx(48F)
    }

    private var FragmentActivity.menuLayoutWidth
        get() = menuLayout.layoutParams.width
        set(width) {
            menuLayout.layoutParams.width = width
        }

    private val FragmentActivity.screenWidthInDp : Int
        get () {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

    private fun showConfigurationFragment() {
        val fragment = ConfigurationFragment.getInstance(numberType, lowerBound, upperBound, digits)
        fragment.listener = this
        addFragmentAnimated(fragment, CONFIGURATION_TAG)
        onCloseDrawer()
    }

    private fun addFragmentAnimated(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_animator, R.animator.slide_out_animator,
                        R.animator.slide_in_animator, R.animator.slide_out_animator)
                .add(R.id.container_fragment, fragment, tag)
                .addToBackStack(null)
                .commit()
    }

    companion object {
        private const val CONFIGURATION_TAG = "configuration_fragment"
    }
}
