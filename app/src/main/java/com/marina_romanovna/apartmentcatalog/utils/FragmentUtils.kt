package com.marina_romanovna.apartmentcatalog.utils

import android.os.Bundle
import androidx.fragment.app.Fragment

fun Fragment.putIntArguments(key: String, value: Int): Fragment {
    return this.apply {
        arguments = Bundle().apply {
            putInt(key, value)
        }
    }
}

fun Fragment.putStringArguments(key: String, value: String): Fragment {
    return this.apply {
        arguments = Bundle().apply {
            putString(key, value)
        }
    }
}

fun Fragment.putModeAndIdArguments(
    keyMode: String,
    modeValue: String,
    keyId: String,
    idValue: Int
): Fragment {
    return this.apply {
        arguments = Bundle().apply {
            putString(keyMode, modeValue)
            putInt(keyId, idValue)
        }
    }
}