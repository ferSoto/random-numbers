package com.zomaotoko.randomnumbers


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.DisplayMetrics
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import com.zomaotoko.randomnumbers.Utils.Companion.dpToPx
import com.zomaotoko.randomnumbers.drawermenu.MenuAdapter
import com.zomaotoko.randomnumbers.generators.NumberType


class MainActivity : FragmentActivity(), ConfigurationFragment.ConfigurationSelector, MenuAdapter.MenuListener {
    companion object {
        private const val CONFIGURATION_TAG = "configuration_fragment"

        private const val PREFERENCES_KEY = "preferences"
        private const val NUMBER_TYPE = "number_type"
        private const val LOWER_BOUND = "lower_bound"
        private const val UPPER_BOUND = "upper_bound"
        private const val DIGITS = "digits"

        private const val DEFAULT_NUMBER_TYPE = 0
        private const val DEFAULT_LOWER_BOUND = 0f
        private const val DEFAULT_UPPER_BOUND = 100f
        private const val DEFAULT_DIGITS = 1
    }

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

        // Menu layout must be 48 dp smaller than screen width
        menuLayoutWidth = screenWidthInDp - dpToPx(48F)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(menuLayout)) {
            onCloseDrawer(null)
        } else {
            super.onBackPressed()
        }
    }

    private fun inflateLayout() {
        generatorFragment = fragmentManager.findFragmentById(R.id.fragment_generate_number) as GenerateNumberFragment
    }

    private fun loadConfiguration() {
        // Get saved configuration. Default -> INTEGER
        with(getSharedPreferences(PREFERENCES_KEY, 0)) {
            numberType = NumberType.fromValue(getInt(NUMBER_TYPE, DEFAULT_NUMBER_TYPE))
            lowerBound = getFloat(LOWER_BOUND, DEFAULT_LOWER_BOUND)
            upperBound = getFloat(UPPER_BOUND, DEFAULT_UPPER_BOUND)
            digits = getInt(DIGITS, DEFAULT_DIGITS)
        }
        generatorFragment.setNumberType(numberType)
        generatorFragment.setBoundaries(lowerBound, upperBound)
        generatorFragment.setDigits(digits)
    }

    private fun showConfigurationFragment() {
        val fragment = ConfigurationFragment.getInstance(numberType, lowerBound, upperBound, digits)
        fragment.listener = this
        addFragmentAnimated(fragment, CONFIGURATION_TAG)
        onCloseDrawer(null)
    }


    // Fragments

    private fun addFragmentAnimated(fragment: Fragment, tag: String) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_animator, R.animator.slide_out_animator,
                        R.animator.slide_in_animator, R.animator.slide_out_animator)
                .add(R.id.container_fragment, fragment, tag)
                .addToBackStack(null)
                .commit()
    }


    // Listeners

    override fun onTypeSelected(type: NumberType) {
        if (numberType == type) return
        numberType = type
        with(getSharedPreferences(PREFERENCES_KEY, 0).edit()) {
            putInt(NUMBER_TYPE, numberType.value)
            apply()
        }
        generatorFragment.setNumberType(numberType)
    }

    override fun onBoundariesSelected(lowerBound: Float, upperBound: Float) {
        this.lowerBound = lowerBound
        this.upperBound = upperBound
        with(getSharedPreferences(PREFERENCES_KEY, 0).edit()) {
            putFloat(LOWER_BOUND, lowerBound)
            putFloat(UPPER_BOUND, upperBound)
            apply()
        }
        generatorFragment.setBoundaries(lowerBound, upperBound)
    }

    override fun onDigitsSelected(digits: Int) {
        this.digits = digits
        with(getSharedPreferences(PREFERENCES_KEY, 0).edit()) {
            putInt(DIGITS, digits)
            apply()
        }
        generatorFragment.setDigits(digits)
    }


    //

    override fun onHomeClick() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            // Remove visible fragment if it's not the main fragment
            supportFragmentManager.popBackStackImmediate()
        }
        onCloseDrawer(null)
    }

    override fun onConfigurationClick() {
        if (supportFragmentManager.findFragmentByTag(CONFIGURATION_TAG) == null) {
            showConfigurationFragment()
        }
    }


    //

    fun onTitleBarButtonClick(view: View) {
        drawerLayout.openDrawer(menuLayout)
    }

    fun onCloseDrawer(view: View?) {
        drawerLayout.closeDrawer(menuLayout)
    }


    // Screen

    private var menuLayoutWidth
        get() = menuLayout.layoutParams.width
        set(width) {
            menuLayout.layoutParams.width = width
        }

    private val screenWidthInDp : Int
        get () {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }
}
