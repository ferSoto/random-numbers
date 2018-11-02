package com.zomaotoko.randomnumbers.ui.menu

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.zomaotoko.randomnumbers.R
import kotlinx.android.synthetic.main.layout_menu_item.view.*

class MenuItem : LinearLayout {
    init {
        inflateLayout()
    }

    private fun inflateLayout() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_menu_item, this)
    }

    var text: String? = null
        set(value) {
            field = value
            itemLabel.text = value
        }

    var icon: Drawable? = null
        set(drawable) {
            field = drawable
            itemIcon.setImageDrawable(drawable)
        }

    // Constructors (all empty)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int)
            : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int)
            : super(context, attrs, defStyleAttr, defStyleRes)
}