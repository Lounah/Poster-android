package com.lounah.core.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.lounah.core.extensions.spToPx
import kotlin.math.max

private const val DEFAULT_TEXT_SIZE_SP = 12f
private const val DEFAULT_TEXT_COLOR_INT = Color.BLACK
private const val TEXT_STYLE_NORMAL = 0
private const val TEXT_STYLE_BOLD = 1
private const val TEXT_STYLE_ITALIC = 2

/**
 *  An implementation of ${TextView}, which shows only a short single lined text.
 *  Could be good for using in RecyclerView's list item.
 *  Supports BOLD, NORMAL, ITALIC text styles.
 */
class SingleLineTextView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttrs: Int) : super(
        context,
        attributeSet,
        defStyleAttrs
    )

    enum class TextStyle(val styleInt: Int) {
        NORMAL(TEXT_STYLE_NORMAL), BOLD(TEXT_STYLE_BOLD), ITALIC(TEXT_STYLE_ITALIC)
    }

    var text: String = ""
        set(value) {
            requestTextMeasure(value)
            field = value
            invalidate()
        }

    var textStyle: TextStyle = TextStyle.NORMAL
        set(value) {
            applyNewTextStyle(value)
            field = value
            invalidate()
        }

    var textSize: Float = DEFAULT_TEXT_SIZE_SP.spToPx()
        set(value) {
            textPaint.textSize = value.spToPx()
            requestTextMeasure(text)
            field = value
            invalidate()
        }

    var textColorRes: Int = DEFAULT_TEXT_COLOR_INT
        set(value) {
            textPaint.color = ContextCompat.getColor(context, value)
            field = value
            invalidate()
        }

    private val textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColorRes
        textSize = this@SingleLineTextView.textSize
        typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    }

    private var measuredTextWidth = -1f
    private var measuredTextHeight = -1f

    init {
        if (isInEditMode) {
            text = "Hello, world!"
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measuredTextWidth.toInt() + paddingRight + paddingLeft,
            measuredTextHeight.toInt() + paddingBottom + paddingTop)
    }

    override fun onDraw(canvas: Canvas) {
        val xPos = max(paddingLeft, paddingRight).toFloat()
        val yPos = (height / 2f - (textPaint.descent() + textPaint.ascent()) / 2)
        canvas.drawText(text, xPos, yPos, textPaint)
    }

    private fun applyNewTextStyle(style: TextStyle) {
        when (style) {
            TextStyle.NORMAL -> textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            TextStyle.BOLD -> textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            TextStyle.ITALIC -> textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        }
    }

    private fun requestTextMeasure(text: String) {
        val bounds = Rect()
        textPaint.getTextBounds(text, 0, text.length, bounds)
        measuredTextHeight = bounds.height().toFloat()
        measuredTextWidth = bounds.width().toFloat()
        requestLayout()
    }
}
