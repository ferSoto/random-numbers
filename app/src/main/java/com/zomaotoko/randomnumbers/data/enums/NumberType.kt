package com.zomaotoko.randomnumbers.data.enums

import com.zomaotoko.randomnumbers.data.exceptions.NoSuchNumberTypeException

enum class NumberType {
    INTEGER, DECIMAL, BINARY;

    val value: Int
        get() = when(this) {
            INTEGER -> 0
            DECIMAL -> 1
            BINARY -> 2
        }

    companion object {
        fun fromValue(value: Int) = when(value) {
            0 -> INTEGER
            1 -> DECIMAL
            2 -> BINARY
            else -> throw NoSuchNumberTypeException()
        }
    }
}
