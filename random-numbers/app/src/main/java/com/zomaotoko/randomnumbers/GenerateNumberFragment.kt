package com.zomaotoko.randomnumbers


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_generate_number.*


class GenerateNumberFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_generate_number, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        generateNumberBtn.setOnClickListener {
            // TODO: show number
        }
    }

    private fun showNumber(text: String) {
        if (isAdded) activity.runOnUiThread({ numberTxt.text = text })
    }
}
