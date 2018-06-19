package com.zomaotoko.randomnumbers.drawermenu

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

class MenuAdapter(private val context: Context, optionsListRes: Int, iconsListRes: Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class ViewHolder(var view: MenuItem) : RecyclerView.ViewHolder(view)

    private val labelList = ArrayList<String>()
    private val iconList = ArrayList<Int>()

    init {
        labelList.addAll(context.resources.getStringArray(optionsListRes))
        context.resources.getIntArray(iconsListRes).map { iconList.add(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(MenuItem(context))

    override fun getItemCount() = labelList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        TODO("Bind views")
    }

    private fun numberConfiguration(view: MenuItem) {

    }

}