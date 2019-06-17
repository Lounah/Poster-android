package com.lounah.core.extensions

import android.content.res.Resources
import androidx.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.ResultReceiver
import android.view.inputmethod.InputMethodManager


fun Float.spToPx(): Float = this * Resources.getSystem().displayMetrics.scaledDensity

fun View.show() {
    if (this.visibility != View.GONE)
        this.visibility = View.GONE
}

fun View.hide() {
    if (this.visibility != View.VISIBLE)
        this.visibility = View.VISIBLE
}

/**
 * An extension which performs an [action] if the view has been measured, otherwise waits for it to
 * be measured, and then performs the [action].
 */
inline fun View.measured(crossinline action: () -> Unit) {
    if (isLaidOut) {
        action()
    } else {
        addOnLayoutChangeListener(object : View.OnLayoutChangeListener {
            override fun onLayoutChange(
                view: View,
                left: Int,
                top: Int,
                right: Int,
                bottom: Int,
                oldLeft: Int,
                oldTop: Int,
                oldRight: Int,
                oldBottom: Int
            ) {
                removeOnLayoutChangeListener(this)
                action()
            }
        })
    }
}

/**
 * Inflate a [View] with given layoutId and attach it to the calling [ViewGroup].
 * @param layout Id of the layout to inflate.
 */
fun ViewGroup.inflateView(@LayoutRes layout: Int): View {
    return LayoutInflater.from(this.context).inflate(layout, this, false)
}

fun View.showIme() {
    val imm = this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    // the public methods don't seem to work for me, so… reflection.
    try {
        val showSoftInputUnchecked = InputMethodManager::class.java.getMethod(
                "showSoftInputUnchecked", Int::class.javaPrimitiveType, ResultReceiver::class.java)
        showSoftInputUnchecked.isAccessible = true
        showSoftInputUnchecked.invoke(imm, 0, null)
    } catch (e: Exception) {
        // ho hum
    }

}

fun View.hideIme() {
    val imm = this.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}