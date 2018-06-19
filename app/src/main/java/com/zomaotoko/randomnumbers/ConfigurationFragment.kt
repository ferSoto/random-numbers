package com.zomaotoko.randomnumbers


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_configuration.*


class ConfigurationFragment : Fragment() {
    enum class NumberType { INTEGER, FLOAT }

    private lateinit var type: NumberType

    companion object {
        private const val TYPE_KEY = "number_type"

        fun getInstance(type: NumberType) = ConfigurationFragment().apply {
            arguments = Bundle().apply {
                putSerializable(TYPE_KEY, type)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments[TYPE_KEY] as NumberType
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater?.inflate(R.layout.fragment_configuration, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (type == NumberType.INTEGER) integerBtn.isChecked = true
        else doubleBtn.isChecked = true
    }
}