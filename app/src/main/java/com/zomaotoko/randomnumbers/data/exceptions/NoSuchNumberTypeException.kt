package com.zomaotoko.randomnumbers.data.exceptions

class NoSuchNumberTypeException() : Exception() {
    override val message: String?
        get() = ERROR_MSG

    companion object {
        private const val ERROR_MSG = "No such number type"
    }
}
