package com.andikas.burogu.data

import android.content.Context
import android.content.SharedPreferences

class IdentifySharedPref(context: Context) {

    companion object {
        private const val SHARED_PREF_USER = "userData"
        private const val USER_NAME = "userName"
        private const val USER_IS_IDENTIFIED = "userIsIdentified"
    }

    private var identifySharedPref: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_USER, Context.MODE_PRIVATE)

    var userName = ""
        get() = identifySharedPref.getString(USER_NAME, "") ?: ""
        set(value) {
            identifySharedPref.edit().putString(USER_NAME, value).apply()
            field = value
        }

    var userIdentified = false
        get() = identifySharedPref.getBoolean(USER_IS_IDENTIFIED, false)
        set(value) {
            identifySharedPref.edit().putBoolean(USER_IS_IDENTIFIED, value).apply()
            field = value
        }

}