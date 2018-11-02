package com.zomaotoko.randomnumbers.utils


import android.app.Activity
import android.content.res.Resources
import android.support.v4.app.FragmentActivity
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*


class Utils private constructor() {

    companion object {
        fun pxToDp(pixels: Int) : Float {
            val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
            return pixels.toFloat() * DisplayMetrics.DENSITY_DEFAULT / displayMetrics.densityDpi
        }

        fun dpToPx(dp: Float) : Int {
            val displayMetrics: DisplayMetrics = Resources.getSystem().displayMetrics
            return (dp * displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT).toInt()
        }

        var FragmentActivity.menuLayoutWidth
            get() = menuLayout.layoutParams.width
            set(width) {
                menuLayout.layoutParams.width = width
            }

        val FragmentActivity.screenWidthInDp : Int
            get () {
                val displayMetrics = DisplayMetrics()
                windowManager.defaultDisplay.getMetrics(displayMetrics)
                return displayMetrics.widthPixels
            }
    }
}