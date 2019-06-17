package com.lounah.core.extensions

import android.content.res.Resources

fun Float.spToPx(): Float = this * Resources.getSystem().displayMetrics.scaledDensity