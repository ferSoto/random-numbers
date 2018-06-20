package com.zomaotoko.randomnumbers


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zomaotoko.randomnumbers.generators.NumberType
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
        return inflater?.inflate(R.layout.fragment_configuration, container, false)?.apply {
            setOnClickListener {
                // Intercept taps
            }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(type) {
            NumberType.INTEGER -> integerBtn.isChecked = true
            NumberType.DOUBLE -> doubleBtn.isChecked = true
            NumberType.BINARY -> binaryBtn.isChecked = true
        }
        setButtonsListeners()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun setButtonsListeners() {
        integerBtn.setOnClickListener { setNumberType(NumberType.INTEGER) }
        doubleBtn.setOnClickListener { setNumberType(NumberType.DOUBLE) }
        binaryBtn.setOnClickListener { setNumberType(NumberType.BINARY) }
    }

    private fun setNumberType(type: NumberType) {
        this.type = type
        listener?.onTypeSelected(this.type)

        // Binary type of number does not need boundaries
        if (type == NumberType.BINARY) {
            hideBoundariesConfig()
        } else {
            showBoundariesConfig()
        }
    }

    private fun showBoundariesConfig() {
        boundariesConfig.visibility = View.VISIBLE
    }

    private fun hideBoundariesConfig() {
        boundariesConfig.visibility = View.GONE
    }
}
