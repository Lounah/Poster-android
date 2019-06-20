package com.lounah.core.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.lounah.core.extensions.addRipple
import com.lounah.core.extensions.dpToPx

class RoundCornerImageView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0)
    : AppCompatImageView(context, attrs) {

    private companion object {
        private const val DEFAULT_RADIUS_DP = 8
    }

    private val radius = DEFAULT_RADIUS_DP.dpToPx().toFloat()
    private var path = Path()
    private var rect: RectF? = null

    private var viewIsDirty = true

    override fun onDraw(canvas: Canvas) {
        if (viewIsDirty) {
            init()
        }
        canvas.clipPath(path)
        super.onDraw(canvas)
    }

    private fun init() {
        rect = RectF(0f, 0f, width.toFloat(), height.toFloat())
        path.addRoundRect(rect, radius, radius, Path.Direction.CW)
        viewIsDirty = false
    }
}