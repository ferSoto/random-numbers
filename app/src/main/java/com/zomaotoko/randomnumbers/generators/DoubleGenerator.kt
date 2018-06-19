package com.zomaotoko.randomnumbers.generators

import java.util.concurrent.ThreadLocalRandom


class DoubleGenerator(private val lowerBound: Double, private val upperBound: Double, private val digits: Int) : Generator {

    override fun getRandomNumber() = ThreadLocalRandom.current().nextDouble(lowerBound, upperBound)

    override fun getRandomNumberString() = String.format("%.${digits}f", getRandomNumber())
}
