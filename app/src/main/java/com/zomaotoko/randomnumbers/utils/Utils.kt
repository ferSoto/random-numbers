package com.zomaotoko.randomnumbers.utils

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
    }
}
