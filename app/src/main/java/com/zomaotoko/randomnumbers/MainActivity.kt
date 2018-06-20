package com.zomaotoko.randomnumbers

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.DisplayMetrics
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import com.zomaotoko.randomnumbers.Utils.Companion.dpToPx
import com.zomaotoko.randomnumbers.drawermenu.MenuAdapter


class MainActivity : FragmentActivity(), ConfigurationFragment.TypeSelector, MenuAdapter.MenuListener {
    private lateinit var numberType: NumberType
    private lateinit var generatorFragment: GenerateNumberFragment

    companion object {
        private const val PREFERENCES_KEY = "preferences"
        private const val NUMBER_TYPE = "number_type"

        private const val CONFIGURATION_TAG = "configuration_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inflateLayout()
        loadConfiguration()

        // Menu layout must be 48 dp smaller than screen width
        setMenuLayoutWidth(screenWidthInDp - dpToPx(48F))
    }

    private fun inflateLayout() {
        generatorFragment = fragmentManager.findFragmentById(R.id.fragment_generate_number) as GenerateNumberFragment
    }

    private fun loadConfiguration() {
        // Get saved configuration. Default -> INTEGER
        with(getSharedPreferences(PREFERENCES_KEY, 0)) {
            numberType = NumberType.fromValue(getInt(NUMBER_TYPE, 0))
        }
        generatorFragment.setNumberType(numberType)
    }

    private fun showConfigurationFragment() {
        val fragment = ConfigurationFragment.getInstance(numberType)
        fragment.listener = this
        addFragmentAnimated(fragment, CONFIGURATION_TAG)
        drawerLayout.closeDrawer(menuLayout)
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


    //

    override fun onConfigurationClick() {
        if (supportFragmentManager.findFragmentByTag(CONFIGURATION_TAG) == null) {
            showConfigurationFragment()
        }
    }


    //

    fun onTitleBarButtonClick(view: View) {
        drawerLayout.openDrawer(menuLayout)
    }


    // Screen

    private fun setMenuLayoutWidth(width: Int) {
        menuLayout.layoutParams.width = width
    }

    private val screenWidthInDp : Int
        get () {
            val displayMetrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }
}
