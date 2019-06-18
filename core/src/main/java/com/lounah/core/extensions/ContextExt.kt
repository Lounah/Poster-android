package com.lounah.core.extensions

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.lounah.core.R


fun Context.getColorCompat(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.getDrawableCompat(@DrawableRes drawableRes: Int): Drawable? {
    return AppCompatResources.getDrawable(this, drawableRes)
}

/**
 *  @param actionToast toast, which will be shown after text copying action
 */
fun Context.copyTextToClipBoard(text: String, label: String?, actionToast: Toast?) {
    val copyLabel = label ?: this.getString(R.string.text_has_been_copied_to_the_clipboard)
    val clipboard = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(copyLabel, text)
    clipboard.primaryClip = clipData
    actionToast?.show()
}