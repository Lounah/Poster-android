package com.lounah.core.persistence

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.SystemClock

class PreferencesHelper(context: Context) {

    companion object {
        const val PREF_FILE_NAME = "poster_pref_file"

        const val PREF_USER_INDEPENDENT_FILE_NAME = "poster_pref_user_independent_file"

        private const val KEY_PIN = "encoded_pin"
        private const val KEY_FINGERPRINT_ENABLED = "fingerprint_enabled"
        private const val KEY_PIN_ENABLED = "pin_enabled"

        private const val KEY_INTRO_FLAG = "intro_flag"

        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_TOKEN_TYPE = "token_type"
        private const val KEY_EXPIRES_IN_THIS_TIME_MS = "expires_in_this_time"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_PIN_WRONG_COUNT = "pin_wrong_count"
        private const val KEY_PIN_WRONG_FIRST_TIME = "pin_wrong_first_time"
        private const val KEY_CURRENT_COMPANY_ID = "current_company_id"
        private const val KEY_CURRENT_COMPANY_NAME = "current_company_name"
        private const val KEY_PUSH_TOKEN = "pushToken"
        private const val KEY_TASKS_CURRENT_AUTHOR_ID = "currentAuthorId"
    }

    private val pref: SharedPreferences
    private val prefUserIndependent: SharedPreferences

    init {
        pref = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
        prefUserIndependent = context.getSharedPreferences(PREF_USER_INDEPENDENT_FILE_NAME, Context.MODE_PRIVATE)
    }

    val accessToken: String?
        get() = pref.getString(KEY_ACCESS_TOKEN, null)

    val refreshToken: String?
        get() = pref.getString(KEY_REFRESH_TOKEN, null)

    val tokenType: String?
        get() = pref.getString(KEY_TOKEN_TYPE, null)

    val tokenExpiresInThisTimeMs: Long
        get() = pref.getLong(KEY_EXPIRES_IN_THIS_TIME_MS, System.currentTimeMillis())

    val isAuthenticated: Boolean
        get() = pref.getString(KEY_ACCESS_TOKEN, null) != null

    var isIntroShown: Boolean
        get() = prefUserIndependent.getBoolean(KEY_INTRO_FLAG, false)
        set(isIntroShown) {
            val prefsEditor = prefUserIndependent.edit()
            prefsEditor.putBoolean(KEY_INTRO_FLAG, isIntroShown).apply()
        }

    var encodedPin: String?
        get() = pref.getString(KEY_PIN, null)
        set(encodedPin) {
            val prefsEditor = pref.edit()
            prefsEditor.putString(KEY_PIN, encodedPin).apply()
        }

    var isFingerprintEnabled: Boolean
        get() = pref.getBoolean(KEY_FINGERPRINT_ENABLED, false)
        set(isFingerprintEnabled) {
            pref.edit().putBoolean(KEY_FINGERPRINT_ENABLED, isFingerprintEnabled).apply()
        }

    var isPinEnabled: Boolean
        get() = pref.getBoolean(KEY_PIN_ENABLED, false)
        set(isPinEnabled) {
            pref.edit().putBoolean(KEY_PIN_ENABLED, isPinEnabled).apply()
        }

    var pushToken: String?
        get() = pref.getString(KEY_PUSH_TOKEN, null)
        set(pushToken) {
            pref.edit().putString(KEY_PUSH_TOKEN, pushToken).apply()
        }

    var currentAuthorId: String?
        get() = pref.getString(KEY_TASKS_CURRENT_AUTHOR_ID, null)
        set(authorId: String?) {
            pref.edit().putString(KEY_TASKS_CURRENT_AUTHOR_ID, authorId).apply()
        }

    fun increasePinWrongCount() {
        val count = pref.getInt(KEY_PIN_WRONG_COUNT, 0)
        val edit = pref.edit()
        if (count == 0) {
            edit.putLong(KEY_PIN_WRONG_FIRST_TIME, SystemClock.elapsedRealtime())
        }
        edit.putInt(KEY_PIN_WRONG_COUNT, count + 1)
        edit.apply()
    }

    fun resetWrongPinCount() {
        val edit = pref.edit()
        edit.putInt(KEY_PIN_WRONG_COUNT, 0)
        edit.putLong(KEY_PIN_WRONG_FIRST_TIME, 0)
        edit.apply()
    }

    fun getPinWrongCount(): Int {
        return pref.getInt(KEY_PIN_WRONG_COUNT, 0)
    }

    fun getPinWrongFirstTime(): Long {
        return pref.getLong(KEY_PIN_WRONG_FIRST_TIME, 0)
    }

//    fun saveToken(tokenResponse: TokenResponse) {
//        pref.edit()
//                .putString(KEY_ACCESS_TOKEN, tokenResponse.accessToken)
//                .putString(KEY_REFRESH_TOKEN, tokenResponse.refreshToken)
//                .putString(KEY_TOKEN_TYPE, tokenResponse.tokenType)
//                .putLong(KEY_EXPIRES_IN_THIS_TIME_MS, System.currentTimeMillis() + tokenResponse.expiresInSec * 1000L - 5000L)
//                .apply()
//    }

    @SuppressLint("ApplySharedPref")
    fun clear() {
        pref.edit().clear().commit()
    }

    fun clearPin() {
        pref.edit().remove(KEY_PIN).apply()
    }
}