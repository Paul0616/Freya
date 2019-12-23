package com.softtehnica.freya.configs

import android.content.Context
import com.encorsa.fidelioseller.utils.Prefs

class ServerConnection(context: Context) {

    private val prefs = Prefs(context)

    var isDemoMode: Boolean
        get() = prefs.isDemoMode
        set(value) {
            prefs.isDemoMode = value
        }

    var serverUrl: String
        get() {
            if (isDemoMode == true) {
                return DEMO_MODE_SERVER_URL
            }
            return prefs.serverUrl ?: ""
        }
        set(value) {
            prefs.serverUrl = value
        }

    var apiKey: String
        get() {
            if (isDemoMode == true) {
                return DEMO_MODE_API_KEY
            }
            return prefs.apiKey ?: ""
        }
        set(value) {
            prefs.apiKey = value
        }

    val isConfigured: Boolean
        get() {
            if (isDemoMode == true) {
                return true
            }
            if (prefs.serverUrl != null && prefs.apiKey != null && !serverUrl.isEmpty() && !apiKey.isEmpty()){
                return true
            }
            return false
        }

    //api urls
    val baseUrl: String
        get() = serverUrl
    val userNeedSync: String
        get() = baseUrl.plus("/User/NeedSync")
    val userFindMany: String
        get() = baseUrl.plus("/User/FindManyUnsynced")
    val ownerGetDetails: String
        get() = baseUrl.plus("/Owner/GetDetails")
}
