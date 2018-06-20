package com.zomaotoko.randomnumbers.generators

enum class NumberType {
    INTEGER, DOUBLE, BINARY;

    val value: Int
        get() = when(this) {
            INTEGER -> 0
            DOUBLE -> 1
            BINARY -> 2
            else -> -1
        }

    companion object {
        fun fromValue(value: Int) = when(value) {
            0 -> INTEGER
            1 -> DOUBLE
            2 -> BINARY
            else -> throw Exception()
        }
    }
}