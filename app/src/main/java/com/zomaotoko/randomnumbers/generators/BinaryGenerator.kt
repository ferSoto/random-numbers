package com.zomaotoko.randomnumbers.generators

import java.util.concurrent.ThreadLocalRandom
import kotlin.math.absoluteValue

class BinaryGenerator : Generator {
    override fun getRandomNumber() = ThreadLocalRandom.current().nextInt().rem(2).absoluteValue

    override fun getRandomNumberString() = getRandomNumber().toString()
}