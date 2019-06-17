package com.lounah.core.logger

import android.util.Log
import com.lounah.core.BuildConfig
import java.lang.Exception

object Logger {
    fun i(message: String) {
        if (BuildConfig.DEBUG)
            Log.i("DEBUG/I", message)
    }

    fun e(message: String) {
        if (BuildConfig.DEBUG)
            Log.e("DEBUG/E", message)
    }

    fun e(ex: Exception) {
        if (BuildConfig.DEBUG)
            Log.e("DEBUG/E", ex.localizedMessage)
    }
}