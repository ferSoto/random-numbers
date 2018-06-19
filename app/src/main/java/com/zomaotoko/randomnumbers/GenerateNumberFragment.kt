package com.zomaotoko.randomnumbers


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zomaotoko.randomnumbers.generators.Generator
import kotlinx.android.synthetic.main.fragment_generate_number.*


class GenerateNumberFragment : Fragment() {
    private var generator: Generator? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_generate_number, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateNumberBtn.setOnClickListener {
            showNumber(generator?.getRandomNumberString())
        }
    }

    private fun showNumber(text: String?) {
        if (isAdded && text != null) activity.runOnUiThread { numberTxt.text = text }
    }
}
