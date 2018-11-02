package com.zomaotoko.randomnumbers.data.local

import com.zomaotoko.randomnumbers.data.enums.NumberType

interface LocalStorage {
    fun retrieveDigits(): Int
    fun saveDigits(digits: Int)

    fun retrieveNumberType(): NumberType
    fun saveNumberType(type: NumberType)

    fun retrieveLowerBound(): Float
    fun saveLowerBound(lowerBound: Float)

    fun retrieveUpperBound(): Float
    fun saveUpperBound(upperBound: Float)
}