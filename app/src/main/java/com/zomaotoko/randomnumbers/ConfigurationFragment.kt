package com.zomaotoko.randomnumbers


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
        private const val ANIMATION_DURATION = 240L
        private const val OUT_OF_SCREEN_POSITION = 1280F

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
            NumberType.INTEGER -> checkInteger()
            NumberType.DOUBLE -> checkDouble()
            NumberType.BINARY -> checkBinary()
        }
        setButtonsListeners()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    //

    private fun checkInteger() {
        integerBtn.isChecked = true
    }

    private fun checkDouble() {
        doubleBtn.isChecked = true
    }

    private fun checkBinary() {
        binaryBtn.isChecked = true
        boundariesConfig.visibility = View.GONE
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
}
