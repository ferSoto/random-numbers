package com.zomaotoko.randomnumbers.generators

import com.zomaotoko.randomnumbers.generators.Generator
import java.util.concurrent.ThreadLocalRandom


class IntGenerator(private val lowerBound: Int, private val upperBound: Int) : Generator {

    override fun getRandomNumber() = ThreadLocalRandom.current().nextInt(lowerBound, upperBound + 1)

    override fun getRandomNumberString() = getRandomNumber().toString()
}