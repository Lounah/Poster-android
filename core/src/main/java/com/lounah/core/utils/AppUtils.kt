package com.lounah.core.utils

import android.os.Build

object AppUtils {
    fun isLollipopOrLater() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}