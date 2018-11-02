package com.zomaotoko.randomnumbers.data.local

import android.content.Context
import android.content.SharedPreferences
import com.zomaotoko.randomnumbers.data.enums.NumberType

class GeneratorSettings(context: Context) : LocalStorage {
    companion object {
        private const val PREFERENCES_KEY = "preferences"
        private const val NUMBER_TYPE = "number_type"
        private const val LOWER_BOUND = "lower_bound"
        private const val UPPER_BOUND = "upper_bound"
        private const val DIGITS = "digits"

        private const val DEFAULT_NUMBER_TYPE = 0
        private const val DEFAULT_LOWER_BOUND = 0f
        private const val DEFAULT_UPPER_BOUND = 100f
        private const val DEFAULT_DIGITS = 1
    }

    private val preferences: SharedPreferences

    init {
        preferences = context.getSharedPreferences(PREFERENCES_KEY, 0)
    }

    override fun retrieveDigits() = preferences.getInt(DIGITS, DEFAULT_DIGITS)

    override fun saveDigits(digits: Int) {
        with(preferences.edit()) {
            putInt(DIGITS, digits)
            apply()
        }
    }

    override fun retrieveNumberType() = NumberType.fromValue(preferences.getInt(NUMBER_TYPE, DEFAULT_NUMBER_TYPE))

    override fun saveNumberType(type: NumberType) {
        with(preferences.edit()) {
            putInt(NUMBER_TYPE, type.value)
            apply()
        }
    }

    override fun retrieveLowerBound() = preferences.getFloat(LOWER_BOUND, DEFAULT_LOWER_BOUND)

    override fun saveLowerBound(lowerBound: Float) {
        with(preferences.edit()) {
            putFloat(LOWER_BOUND, lowerBound)
            apply()
        }
    }

    override fun retrieveUpperBound() = preferences.getFloat(UPPER_BOUND, DEFAULT_UPPER_BOUND)

    override fun saveUpperBound(upperBound: Float) {
        with(preferences.edit()) {
            putFloat(UPPER_BOUND, upperBound)
            apply()
        }
    }

}