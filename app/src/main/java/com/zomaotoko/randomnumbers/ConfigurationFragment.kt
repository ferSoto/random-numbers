package com.zomaotoko.randomnumbers


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_configuration.*


class ConfigurationFragment : Fragment() {
    interface TypeSelector {
        fun onTypeSelected(type: NumberType)
    }

    private lateinit var type: NumberType
    var listener: TypeSelector? = null

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
        setButtonsListeners()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setButtonsListeners() {
        integerBtn.setOnClickListener { setNumberType(NumberType.INTEGER) }
        doubleBtn.setOnClickListener { setNumberType(NumberType.DOUBLE) }
    }

    private fun setNumberType(type: NumberType) {
        this.type = type
        listener?.onTypeSelected(this.type)
    }

    // Listener
}

enum class NumberType {
    INTEGER, DOUBLE;

    val value: Int
        get() = when(this) {
            INTEGER -> 0
            DOUBLE -> 1
            else -> -1
        }

    companion object {
        fun fromValue(value: Int) = when(value) {
            0 -> INTEGER
            1 -> DOUBLE
            else -> throw Exception()
        }
    }
}
