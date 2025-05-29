package com.example.simplenote.data.local

import android.content.Context

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    fun saveTokens(access: String, refresh: String) {
        prefs.edit()
            .putString("access_token", access)
            .putString("refresh_token", refresh)
            .apply()
    }

    fun saveAccessToken(access: String) {
        prefs.edit()
            .putString("access_token", access)
            .apply()
    }

    fun saveRefreshToken(refresh: String) {
        prefs.edit()
            .putString("refresh_token", refresh)
            .apply()
    }

    fun getAccessToken(): String? = prefs.getString("access_token", null)
    fun getRefreshToken(): String? = prefs.getString("refresh_token", null)

    fun clearTokens() {
        prefs.edit().clear().apply()
    }
}
