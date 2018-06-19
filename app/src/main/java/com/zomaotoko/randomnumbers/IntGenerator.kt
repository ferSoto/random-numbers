package com.zomaotoko.randomnumbers

import java.util.concurrent.ThreadLocalRandom


class IntGenerator(lowerBound: Int, upperBound: Int) : Generator {
    val lowerBound = lowerBound
    val upperBound = upperBound

    override fun getRandomNumber(): Int {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1)
    }

    override fun getRandomNumberString(): String {
        return  getRandomNumber().toString()
    }
}