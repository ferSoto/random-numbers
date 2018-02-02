package com.zomaotoko.randomnumbers

import java.util.concurrent.ThreadLocalRandom


class RandomInt(lowerBound: Int, upperBound: Int) : RandomNumber {
    val lowerBound = lowerBound
    val upperBound = upperBound

    override fun getRandomNumber(): Int {
        return ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1)
    }

    override fun getRandomNumberString(): String {
        return  getRandomNumber().toString()
    }
}