package com.marina_romanovna.apartmentcatalog.utils

fun parseStringToInt(value: String?, defValue: Int): Int {
    return try {
        value?.toInt() ?: defValue
    } catch (e: Exception) {
        defValue
    }
}

fun parseStringToDouble(value: String?, defValue: Double): Double {
    return try {
        value?.toDouble() ?: defValue
    } catch (e: Exception) {
        defValue
    }
}