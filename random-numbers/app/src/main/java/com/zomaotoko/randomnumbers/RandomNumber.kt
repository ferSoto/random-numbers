package com.zomaotoko.randomnumbers


interface RandomNumber<T> {
    fun getRandomNumber() : T
    fun getRandomNumberString() : String
}