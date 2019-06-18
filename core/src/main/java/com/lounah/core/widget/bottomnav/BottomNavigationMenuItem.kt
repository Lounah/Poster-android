package com.lounah.core.widget.bottomnav

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.lounah.core.extensions.getDrawableCompat
import com.lounah.core.extensions.inflateViewWithAttachingToRoot
import kotlinx.android.synthetic.main.view_bottom_navigation_menu_item.view.*
import android.graphics.PorterDuff
import com.lounah.core.R
import com.lounah.core.extensions.getColorCompat


class BottomNavigationMenuItem : FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        inflateViewWithAttachingToRoot(R.layout.view_bottom_navigation_menu_item)
    }

    fun setTextSize(textSizeSp: Float) {
        bottomNavMenuItemTitle.textSize = textSizeSp
    }

    fun setTextTitle(textTitle: String) {
        bottomNavMenuItemTitle.text = textTitle
    }

    fun setDrawableRes(drawableRes: Int) {
        bottomNavMenuItemDrawable.setImageDrawable(context.getDrawableCompat(drawableRes))
    }

    fun setBaseColor(baseColorRes: Int) {
        bottomNavMenuItemTitle.textColorRes = baseColorRes
        bottomNavMenuItemDrawable.setColorFilter(context.getColorCompat(baseColorRes), PorterDuff.Mode.SRC_ATOP)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        bottomNavMenuItemDrawable.setOnClickListener(l)
    }
}