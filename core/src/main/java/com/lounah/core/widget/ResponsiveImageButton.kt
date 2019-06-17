package com.lounah.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageButton
import com.lounah.core.utils.AppUtils

class ResponsiveImageButton : AppCompatImageButton {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    animate()
                            .scaleX(0.8f)
                            .scaleY(0.8f)
                            .start()
                    if (AppUtils.isLollipopOrLater())
                        elevation = 4f
                }
                MotionEvent.ACTION_UP -> {
                    releaseScale()
                }
                MotionEvent.ACTION_OUTSIDE -> {
                    releaseScale()
                }
                MotionEvent.ACTION_MOVE -> {
                    releaseScale()
                }
            }
            return@setOnTouchListener false
        }
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        scaleX = 1f
        scaleY = 1f

        if (AppUtils.isLollipopOrLater())
            elevation = 0f
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scaleX = 1f
        scaleY = 1f

        if (AppUtils.isLollipopOrLater())
            elevation = 0f
    }

    private fun releaseScale() {
        animate()
                .scaleX(1f)
                .scaleY(1f)
                .start()

        if (AppUtils.isLollipopOrLater())
        elevation = 0f
    }
}