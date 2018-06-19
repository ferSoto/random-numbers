package com.zomaotoko.randomnumbers.generators


interface Generator {
    fun getRandomNumber() : Any
    fun getRandomNumberString() : String
}