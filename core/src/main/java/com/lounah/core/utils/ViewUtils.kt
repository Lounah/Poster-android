package com.lounah.core.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.os.Build.VERSION_CODES.LOLLIPOP
import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.palette.graphics.Palette
import android.text.TextPaint
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi

/**
 * Utility methods for working with Views.
 */
object ViewUtils {

    val BACKGROUND_COLOR = AnimUtils.createIntProperty(object : AnimUtils.IntProp<View>("backgroundColor") {
        override operator fun set(view: View, color: Int) {
            view.setBackgroundColor(color)
        }

        override operator fun get(view: View): Int {
            val d = view.background
            return (d as? ColorDrawable)?.color ?: Color.TRANSPARENT
        }
    })

    val TEXT_COLOR = AnimUtils.createIntProperty(object : AnimUtils.IntProp<TextView>("textColor") {
        override operator fun set(textView: TextView, textColor: Int) {
            textView.setTextColor(textColor)
        }

        override operator fun get(textView: TextView): Int {
            return textView.currentTextColor
        }
    })

    val DRAWABLE_ALPHA = AnimUtils.createIntProperty(object : AnimUtils.IntProp<Drawable>("alpha") {
        override operator fun set(drawable: Drawable, alpha: Int) {
            drawable.alpha = alpha
        }

        override operator fun get(drawable: Drawable): Int {
            return drawable.alpha
        }
    })

    val IMAGE_ALPHA = AnimUtils.createIntProperty(object : AnimUtils.IntProp<ImageView>("imageAlpha") {
        override operator fun set(imageView: ImageView, alpha: Int) {
            imageView.imageAlpha = alpha
        }

        override operator fun get(imageView: ImageView): Int {
            return imageView.imageAlpha
        }
    })

    @RequiresApi(LOLLIPOP)
    val CIRCULAR_OUTLINE: ViewOutlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            outline.setOval(view.paddingLeft,
                    view.paddingTop,
                    view.width - view.paddingRight,
                    view.height - view.paddingBottom)
        }
    }

    fun getActionBarSize(context: Context): Int {
        val value = TypedValue()
        context.theme.resolveAttribute(android.R.attr.actionBarSize, value, true)
        return TypedValue.complexToDimensionPixelSize(
                value.data, context.resources.displayMetrics)
    }

    /**
     * Determine if the navigation bar will be on the bottom of the screen, based on logic in
     * PhoneWindowManager.
     */
    fun isNavBarOnBottom(context: Context): Boolean {
        val res = context.resources
        val cfg = context.resources.configuration
        val dm = res.displayMetrics
        val canMove = dm.widthPixels != dm.heightPixels && cfg.smallestScreenWidthDp < 600
        return !canMove || dm.widthPixels < dm.heightPixels
    }

    @RequiresApi(LOLLIPOP)
    fun createRipple(
        @ColorInt color: Int,
        @FloatRange(from = 0.0, to = 1.0) alpha: Float,
        bounded: Boolean
    ): RippleDrawable {
        var color = color
        color = ColorUtils.modifyAlpha(color, alpha)
        return RippleDrawable(ColorStateList.valueOf(color), null,
                if (bounded) ColorDrawable(Color.WHITE) else null)
    }

    @RequiresApi(LOLLIPOP)
    fun createRipple(
        palette: Palette?,
        @FloatRange(from = 0.0, to = 1.0) darkAlpha: Float,
        @FloatRange(from = 0.0, to = 1.0) lightAlpha: Float,
        @ColorInt fallbackColor: Int,
        bounded: Boolean
    ): RippleDrawable {
        var rippleColor = fallbackColor
        if (palette != null) {
            // try the named swatches in preference order
            if (palette.vibrantSwatch != null) {
                rippleColor = ColorUtils.modifyAlpha(palette.vibrantSwatch!!.rgb, darkAlpha)

            } else if (palette.lightVibrantSwatch != null) {
                rippleColor = ColorUtils.modifyAlpha(palette.lightVibrantSwatch!!.rgb,
                        lightAlpha)
            } else if (palette.darkVibrantSwatch != null) {
                rippleColor = ColorUtils.modifyAlpha(palette.darkVibrantSwatch!!.rgb,
                        darkAlpha)
            } else if (palette.mutedSwatch != null) {
                rippleColor = ColorUtils.modifyAlpha(palette.mutedSwatch!!.rgb, darkAlpha)
            } else if (palette.lightMutedSwatch != null) {
                rippleColor = ColorUtils.modifyAlpha(palette.lightMutedSwatch!!.rgb,
                        lightAlpha)
            } else if (palette.darkMutedSwatch != null) {
                rippleColor = ColorUtils.modifyAlpha(palette.darkMutedSwatch!!.rgb, darkAlpha)
            }
        }
        return RippleDrawable(ColorStateList.valueOf(rippleColor), null,
                if (bounded) ColorDrawable(Color.WHITE) else null)
    }

    fun setLightStatusBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }

    fun clearLightStatusBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            view.systemUiVisibility = flags
        }
    }

    /**
     * Recursive binary search to find the best size for the text.
     *
     * Adapted from https://github.com/grantland/android-autofittextview
     */
    fun getSingleLineTextSize(
        text: String,
        paint: TextPaint,
        targetWidth: Float,
        low: Float,
        high: Float,
        precision: Float,
        metrics: DisplayMetrics
    ): Float {
        val mid = (low + high) / 2.0f

        paint.textSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, metrics)
        val maxLineWidth = paint.measureText(text)

        return if (high - low < precision) {
            low
        } else if (maxLineWidth > targetWidth) {
            getSingleLineTextSize(text, paint, targetWidth, low, mid, precision, metrics)
        } else if (maxLineWidth < targetWidth) {
            getSingleLineTextSize(text, paint, targetWidth, mid, high, precision, metrics)
        } else {
            mid
        }
    }

    /**
     * Determines if two views intersect in the window.
     */
    fun viewsIntersect(view1: View?, view2: View?): Boolean {
        if (view1 == null || view2 == null) return false

        val view1Loc = IntArray(2)
        view1.getLocationOnScreen(view1Loc)
        val view1Rect = Rect(view1Loc[0],
                view1Loc[1],
                view1Loc[0] + view1.width,
                view1Loc[1] + view1.height)
        val view2Loc = IntArray(2)
        view2.getLocationOnScreen(view2Loc)
        val view2Rect = Rect(view2Loc[0],
                view2Loc[1],
                view2Loc[0] + view2.width,
                view2Loc[1] + view2.height)
        return view1Rect.intersect(view2Rect)
    }

    fun setPaddingStart(view: View, paddingStart: Int) {
        view.setPaddingRelative(paddingStart,
                view.paddingTop,
                view.paddingEnd,
                view.paddingBottom)
    }

    fun setPaddingTop(view: View, paddingTop: Int) {
        view.setPaddingRelative(view.paddingStart,
                paddingTop,
                view.paddingEnd,
                view.paddingBottom)
    }

    fun setPaddingEnd(view: View, paddingEnd: Int) {
        view.setPaddingRelative(view.paddingStart,
                view.paddingTop,
                paddingEnd,
                view.paddingBottom)
    }

    fun setPaddingBottom(view: View, paddingBottom: Int) {
        view.setPaddingRelative(view.paddingStart,
                view.paddingTop,
                view.paddingEnd,
                paddingBottom)
    }
}