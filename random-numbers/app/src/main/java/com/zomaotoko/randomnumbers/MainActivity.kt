package com.zomaotoko.randomnumbers


import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

import com.zomaotoko.randomnumbers.Utils.Companion.dpToPx


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Menu layout must be 48 dp smaller than screen width
        setMenuLayoutWidth(getScreenWidthInDp() - dpToPx(48F))
        this.menuOptionNumberType.setOnClickListener {
            // do stuffs
        }
    }

    fun onTitleBarButtonClick(view: View) {
        drawerLayout.openDrawer(menuLayout)
    }

    private fun setMenuLayoutWidth(width: Int) {
        menuLayout.layoutParams.width = width
    }

    private fun getScreenWidthInDp() : Int {
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}
