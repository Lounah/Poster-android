package com.lounah.core.widget

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.lounah.core.R
import com.lounah.core.extensions.dpToPx
import com.lounah.core.extensions.hide
import com.lounah.core.extensions.show
import kotlinx.android.synthetic.main.view_poster_toolbar.view.*

const val TITLE_ANIMATION_DURATION_MS = 700L

class PosterToolbar : ConstraintLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_poster_toolbar, this, true)
        textViewTitle.apply {
            textStyle = SingleLineTextView.TextStyle.BOLD
            textSize = 25f
            text = "Feed"
        }
    }

    fun setTitle(title: String) {
        textViewTitle.text = title
        textViewTitle.animate().withLayer()
                .setDuration(TITLE_ANIMATION_DURATION_MS)
                .alpha(1f)
                .start()
    }

    fun setNavigationIcon(src: Drawable, onClickListener: () -> (Unit)) {
        (textViewTitle.layoutParams as MarginLayoutParams).leftMargin = 64.dpToPx()
//        butt.apply {
//            setImageDrawable(src)
//            show()
//            setOnClickListener {
//                onClickListener.invoke()
//            }
//        }
    }

    fun setMenuIcon(src: Drawable, onClickListener: () -> (Unit)) {
//        buttonMenu.apply {
//            show()
////            setImageDrawable(src)
//            setOnClickListener {
//                onClickListener.invoke()
//            }
//        }
    }

    fun hideMenuIcon() {
//        buttonMenu.hide()
    }

    fun hideNavigationIcon() {
//        buttonToolbarBack.hide()
    }

    fun setShouldShowNavigationIcon(shouldShowNavigationIcon: Boolean) {

    }

    fun setShouldShowElevation(shouldShowElevation: Boolean) {
        if (shouldShowElevation) {
            viewElevation.show()
        } else {
            viewElevation.hide()
        }
    }
}