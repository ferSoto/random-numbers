package com.zomaotoko.randomnumbers

import java.util.concurrent.ThreadLocalRandom


class RandomDouble(lowerBound: Double, upperBound: Double, digits: Int) : RandomNumber {
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
