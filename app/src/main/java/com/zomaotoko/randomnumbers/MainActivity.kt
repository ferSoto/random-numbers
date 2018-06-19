package com.zomaotoko.randomnumbers


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import com.zomaotoko.randomnumbers.Utils.Companion.dpToPx


class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menu layout must be 48 dp smaller than screen width
        setMenuLayoutWidth(screenWidthInDp - dpToPx(48F))
        this.menuOptionNumberType.setOnClickListener {
            showConfiguration()
        }
    }

    private fun showConfiguration() {
        addFragmentAnimated(ConfigurationFragment.getInstance(ConfigurationFragment.NumberType.INTEGER))
        drawerLayout.closeDrawer(menuLayout)
    }


    // Fragments
    private fun addFragmentAnimated(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.animator.slide_in_animator, R.animator.slide_out_animator,
                        R.animator.slide_in_animator, R.animator.slide_out_animator)
                .add(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit()
    }


    // Listeners

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
