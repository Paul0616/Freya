package com.encorsa.fidelioseller.utils

import android.content.Context
import androidx.preference.PreferenceManager
import com.softtehnica.freya.configs.*

class Prefs(context: Context) {
    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    var serverUrl: String?
        get() = prefs.getString(SERVER_URL, null)
        set(value) = prefs.edit().putString(SERVER_URL, value).apply()

    var apiKey: String?
        get() = prefs.getString(API_KEY, null)
        set(value) = prefs.edit().putString(API_KEY, value).apply()

    var isDemoMode: Boolean
        get() = prefs.getBoolean(IS_DEMO_MODE, false)
        set(value) = prefs.edit().putBoolean(IS_DEMO_MODE, value).apply()

    fun resetPrefs(){
        prefs.edit().remove(SERVER_URL).apply()
        prefs.edit().remove(API_KEY).apply()
        prefs.edit().remove(IS_DEMO_MODE).apply()
    }
}