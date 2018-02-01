package com.zomaotoko.randomnumbers

import java.util.concurrent.ThreadLocalRandom

/**
 * Created by ferso on 1/31/2018.
 */
class RandomDouble(lowerBound: Double, upperBound: Double, digits: Int) : RandomNumber<Double> {
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