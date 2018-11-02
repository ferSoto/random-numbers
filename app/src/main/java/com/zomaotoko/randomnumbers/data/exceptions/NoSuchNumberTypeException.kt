package com.zomaotoko.randomnumbers.data.exceptions

class NoSuchNumberTypeException() : Exception() {
    companion object {
        private const val ERROR_MSG = "No such number type"
    }
    override val message: String?
        get() = ERROR_MSG
}