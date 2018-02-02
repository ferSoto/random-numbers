package com.zomaotoko.randomnumbers

import java.util.concurrent.ThreadLocalRandom


class DoubleGenerator(lowerBound: Double, upperBound: Double, digits: Int) : Generator {
    val lowerBound = lowerBound
    val upperBound = upperBound
    val digits = digits

    override fun getRandomNumber(): Double {
        return ThreadLocalRandom.current().nextDouble(lowerBound, upperBound)
    }

    override fun getRandomNumberString(): String {
        return String.format("%.${digits}f", getRandomNumber())
    }
}
