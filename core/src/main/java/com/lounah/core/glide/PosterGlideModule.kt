package com.lounah.core.glide

import androidx.core.app.ActivityManagerCompat.isLowRamDevice
import android.content.Context.ACTIVITY_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.ActivityManager
import android.content.Context
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.module.AppGlideModule


@GlideModule
class PosterGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        RequestOptions().apply {
            val activityManager = context.getSystemService(ACTIVITY_SERVICE) as ActivityManager

            format(if (activityManager.isLowRamDevice) DecodeFormat.PREFER_RGB_565
            else DecodeFormat.PREFER_ARGB_8888)

            disallowHardwareConfig()

            builder.setDefaultRequestOptions(this)
        }
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}