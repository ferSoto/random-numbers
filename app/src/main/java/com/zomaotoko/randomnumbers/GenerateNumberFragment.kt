package com.zomaotoko.randomnumbers


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zomaotoko.randomnumbers.generators.*
import kotlinx.android.synthetic.main.fragment_generate_number.*


class GenerateNumberFragment : Fragment() {
    private lateinit var generator: Generator
    private lateinit var type: NumberType
    private var lowerBound: Float = 0f
    private var upperBound: Float = 0f

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_generate_number, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateNumberBtn.setOnClickListener {
            showNumber(generator.getRandomNumberString())
        }
    }

    fun setNumberType(type: NumberType) {
        this.type = type
        refreshGenerator()
    }

    fun setBoundaries(lowerBound: Float, upperBound: Float) {
        this.lowerBound = lowerBound
        this.upperBound = upperBound
        refreshGenerator()
    }

    private fun refreshGenerator() {
        generator = when(type) {
            NumberType.INTEGER -> IntGenerator(lowerBound.toInt(), upperBound.toInt())
            NumberType.DECIMAL -> DecimalGenerator(lowerBound, upperBound, 2)
            else -> BinaryGenerator()
        }
    }

    private fun showNumber(text: String?) {
        if (isAdded && text != null) activity.runOnUiThread { numberTxt.text = text }
    }
}
