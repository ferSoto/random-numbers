package com.zomaotoko.randomnumbers.generators

import java.util.concurrent.ThreadLocalRandom


class DecimalGenerator(private val lowerBound: Float, private val upperBound: Float, private val digits: Int) : Generator {

    override fun getRandomNumber() = ThreadLocalRandom.current().nextDouble(lowerBound.toDouble(), upperBound.toDouble())

    override fun getRandomNumberString() = String.format("%.${digits}f", getRandomNumber())
}
