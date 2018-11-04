package com.zomaotoko.randomnumbers.ui.menu

import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MenuAdapter(private val context: FragmentActivity, optionsListRes: Int, iconsListRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val labelList = context.resources.getStringArray(optionsListRes)
    private val iconList = context.resources.obtainTypedArray(iconsListRes)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(MenuItem(context))

    override fun getItemCount() = labelList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is ViewHolder) {
            with(holder.view) {
                text = labelList[position]
                icon = iconList.getDrawable(position)
                addListener(this, position)
            }
        }
    }

    private fun addListener(view: MenuItem, position: Int) {
        if (context is MenuListener) {
            when(position) {
                0 -> view.setOnClickListener { context.onHomeClick() }
                1 -> view.setOnClickListener { context.onConfigurationClick() }
            }
        }
    }

    class ViewHolder(var view: MenuItem) : RecyclerView.ViewHolder(view)

    interface MenuListener {
        fun onHomeClick()
        fun onConfigurationClick()
    }
}
