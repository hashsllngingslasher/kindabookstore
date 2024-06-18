package com.example.kindabookstore.data.local.prefs

import android.content.Context

class Prefs(
    context: Context
) {
    private val prefs = context.getSharedPreferences(
        "prefs", Context.MODE_PRIVATE
    )


    fun makeFirstLaunch() {
        prefs.edit().putBoolean("firstLaunch", false).apply()
    }

    fun isFirstLaunch(): Boolean {
        return prefs.getBoolean("firstLaunch", true)
    }
}
