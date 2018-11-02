package com.zomaotoko.randomnumbers.ui.config


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.zomaotoko.randomnumbers.R
import com.zomaotoko.randomnumbers.data.generators.NumberType
import kotlinx.android.synthetic.main.fragment_configuration.*
import kotlin.math.floor


class ConfigurationFragment : Fragment() {
    interface ConfigurationSelector {
        fun onTypeSelected(type: NumberType)
        fun onBoundariesSelected(lowerBound: Float, upperBound: Float)
        fun onDigitsSelected(digits: Int)
    }

    companion object {
        private const val TYPE_KEY = "number_type"
        private const val LOWER_BOUND_KEY = "lower_bound"
        private const val UPPER_BOUND_KEY = "upper_bound"
        private const val DIGITS_KEY = "digits"

        private const val ANIMATION_DURATION = 240L
        private const val OUT_OF_SCREEN_POSITION = 1280F

        fun getInstance(type: NumberType, lowerBound: Float, upperBound: Float, digits: Int) = ConfigurationFragment().apply {
            arguments = Bundle().apply {
                putSerializable(TYPE_KEY, type)
                putFloat(LOWER_BOUND_KEY, lowerBound)
                putFloat(UPPER_BOUND_KEY, upperBound)
                putInt(DIGITS_KEY, digits)
            }
        }
    }

    private lateinit var type: NumberType
    private var lowerBound: Float = 0f
    private var upperBound: Float = 0f
    private var digits: Int = 1
    var listener: ConfigurationSelector? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = arguments[TYPE_KEY] as NumberType
        lowerBound = arguments.getFloat(LOWER_BOUND_KEY, lowerBound)
        upperBound = arguments.getFloat(UPPER_BOUND_KEY, upperBound)
        digits = arguments.getInt(DIGITS_KEY, digits)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_configuration, container, false)?.apply {
            setOnClickListener { // Intercept taps
            }
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        when(type) {
            NumberType.INTEGER -> checkInteger()
            NumberType.DECIMAL -> checkDecimal()
            NumberType.BINARY -> checkBinary()
        }
        configureBounds()
        configureButtonsListeners()
        configureEditTextListeners()
        configureSeekBar()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    //

    private fun checkInteger() {
        integerBtn.isChecked = true
        configureInputs()
        digitsConfig.visibility = View.GONE
    }

    private fun checkDecimal() {
        decimalBtn.isChecked = true
        configureInputs()
        digitsConfig.visibility = View.VISIBLE
    }

    private fun checkBinary() {
        binaryBtn.isChecked = true
        boundariesConfig.visibility = View.GONE
        digitsConfig.visibility = View.GONE
    }

    private fun configureBounds() {
        lowerBoundEdit.setText(lowerBound.toString(), TextView.BufferType.EDITABLE)
        upperBoundEdit.setText(upperBound.toString(), TextView.BufferType.EDITABLE)
    }

    private fun configureButtonsListeners() {
        integerBtn.setOnClickListener { setNumberType(NumberType.INTEGER) }
        decimalBtn.setOnClickListener { setNumberType(NumberType.DECIMAL) }
        binaryBtn.setOnClickListener { setNumberType(NumberType.BINARY) }
    }

    private fun configureEditTextListeners() {
        // TODO("Validate boundaries")
        lowerBoundEdit.addTextChangedListener(TextWatcherImplementation { text ->
            if (text.isEmpty()) return@TextWatcherImplementation
            lowerBound = text.toFloat()
            listener?.onBoundariesSelected(lowerBound, upperBound)
        })

        upperBoundEdit.addTextChangedListener(TextWatcherImplementation { text ->
            if (text.isEmpty()) return@TextWatcherImplementation
            upperBound = text.toFloat()
            listener?.onBoundariesSelected(lowerBound, upperBound)
        })
    }

    private fun configureSeekBar() {
        seekBar.progress = (digits - 1) * 25
        digitsTxt.setText("$digits", TextView.BufferType.EDITABLE)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            var lastValue = digits

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                seekBar.progress = fixedPosition
                listener?.onDigitsSelected(fixedValue)
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                lastValue = p1
                digitsTxt.setText("$fixedValue", TextView.BufferType.EDITABLE)
            }

            private val fixedPosition: Int
                get() = (fixedValue - 1) * 25

            private val fixedValue: Int
                get() = 1 + floor((lastValue / 25f)).toInt()
        })
    }

    private fun setNumberType(type: NumberType) {
        this.type = type
        listener?.onTypeSelected(this.type)

        updateUI()
        configureInputs()
    }

    private fun updateUI() {
        when(type) {
            NumberType.INTEGER -> {
                showBoundariesConfig()
                hideDigitsConfig()
            }
            NumberType.DECIMAL -> {
                showBoundariesConfig()
                showDigitsConfig()
            }
            else -> {
                hideBoundariesConfig()
                hideDigitsConfig()
            }
        }
    }

    private fun configureInputs() {
        var inputType = InputType.TYPE_CLASS_NUMBER
        if (type == NumberType.DECIMAL) {
            // Allow to use decimal point
            inputType = inputType or InputType.TYPE_NUMBER_FLAG_DECIMAL
        }
        lowerBoundEdit.inputType = inputType
        upperBoundEdit.inputType = inputType
    }


    // Animation

    private fun showBoundariesConfig() {
        if (boundariesConfig.visibility == View.VISIBLE) return

        // Show boundaries configuration's layout sliding in from right
        boundariesConfig.visibility = View.VISIBLE
        slide(boundariesConfig, OUT_OF_SCREEN_POSITION, 0f) {
            boundariesConfig.visibility = View.VISIBLE
        }
    }

    private fun hideBoundariesConfig() {
        if (boundariesConfig.visibility == View.GONE) return

        // Show boundaries configuration's layout sliding out to left
        slide(boundariesConfig, 0f, -OUT_OF_SCREEN_POSITION) {
            boundariesConfig.visibility = View.GONE
        }
    }

    private fun showDigitsConfig() {
        if (digitsConfig.visibility == View.VISIBLE) return

        // Show digits configuration's layout sliding in from right
        digitsConfig.visibility = View.VISIBLE
        slide(digitsConfig, OUT_OF_SCREEN_POSITION, 0f) {
            digitsConfig.visibility = View.VISIBLE
        }
    }

    private fun hideDigitsConfig() {
        if (digitsConfig.visibility == View.GONE) return

        // Hide digits configuration's layout sliding out to left
        slide(digitsConfig, 0f, -OUT_OF_SCREEN_POSITION) {
            digitsConfig.visibility = View.GONE
        }
    }

    private fun slide(view: View, from: Float, to: Float, after: (() -> Unit)) {
        view.translationX = from
        view.animate()
                .setDuration(ANIMATION_DURATION)
                .translationX(to)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        after()
                    }
                }).start()
    }


    // Helper inner class

    private class TextWatcherImplementation(private val completion: (String) -> Unit) : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            if (p0 != null) {
                completion(p0.toString())
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

    }
}
