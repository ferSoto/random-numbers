package com.zomaotoko.randomnumbers.data.generators

interface Generator {
    fun getRandomNumber() : Any
    fun getRandomNumberString() : String
}
