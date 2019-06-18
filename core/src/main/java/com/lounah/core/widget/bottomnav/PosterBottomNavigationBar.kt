package com.lounah.core.widget.bottomnav

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlinx.android.parcel.Parcelize

/**
 *  STILL IN DEVELOPMENT
 */
class PosterBottomNavigationBar : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    interface Callbacks {
        fun onMenuItemReSelected(itemId: Int)
        fun onMenuItemSelected(itemId: Int)
    }

    @Parcelize
    data class MenuItem(
        val id: Int,
        val title: String,
        val drawableRes: Int? = null,
        val selectedColorRes: Int,
        val baseColorRes: Int
    ) : Parcelable

    var callbacks: Callbacks? = null

    private val menuItems = mutableListOf<MenuItem>()

    private var currentlySelectedMenuItem: MenuItem? = null

    init {
        orientation = HORIZONTAL
    }

    fun selectItemWithId(itemId: Int) {
        val item = menuItems.findLast { it.id == itemId }

        item?.let {
            (this@PosterBottomNavigationBar.getChildAt(menuItems.indexOf(it)) as BottomNavigationMenuItem).apply {
                setBaseColor(item.selectedColorRes)
            }

            if (currentlySelectedMenuItem != null)
            (this@PosterBottomNavigationBar.getChildAt(menuItems.indexOf(currentlySelectedMenuItem
                    ?: 0)) as BottomNavigationMenuItem).apply {
                if (currentlySelectedMenuItem != it)
                    setBaseColor(it.baseColorRes)
            }

            currentlySelectedMenuItem = item
        }
    }

    fun addMenuItems(items: List<MenuItem>) {
        menuItems.addAll(items)

        weightSum = menuItems.size.toFloat()

        menuItems.forEachIndexed { index, menuItem ->
            BottomNavigationMenuItem(context).apply {
                LayoutParams(
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT,
                        1.0f
                ).let { this.layoutParams = it }

                setTextTitle(menuItem.title)
                setTextSize(14f)

                menuItem.drawableRes?.let { setDrawableRes(it) }

                setOnClickListener {
                    if (currentlySelectedMenuItem != menuItem)
                        callbacks?.onMenuItemSelected(menuItem.id)
                    else callbacks?.onMenuItemReSelected(menuItem.id)

                    (this@PosterBottomNavigationBar.getChildAt(index) as BottomNavigationMenuItem).apply {
                        setBaseColor(menuItem.selectedColorRes)
                    }

                    if (currentlySelectedMenuItem != null)
                        (this@PosterBottomNavigationBar.getChildAt(menuItems.indexOf(currentlySelectedMenuItem
                                ?: 0)) as BottomNavigationMenuItem).apply {
                            if (currentlySelectedMenuItem != menuItem)
                                setBaseColor(menuItem.baseColorRes)
                        }

                    currentlySelectedMenuItem = menuItem
                }

                setBaseColor(menuItem.baseColorRes)

            }.let {
                this@PosterBottomNavigationBar.addView(it)
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val superState = super.onSaveInstanceState()
        val savedState = SavedState(superState)

        savedState.menuItems = menuItems
        savedState.selectedItemIndex = menuItems.indexOf(currentlySelectedMenuItem ?: 0)

        return savedState
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }

        menuItems.clear()
        menuItems.addAll(state.menuItems)
        currentlySelectedMenuItem = menuItems[state.selectedItemIndex]

        (getChildAt(state.selectedItemIndex) as BottomNavigationMenuItem).apply {
            setBaseColor(currentlySelectedMenuItem?.selectedColorRes ?: 0)
        }

        super.onRestoreInstanceState(state.superState)
    }

    internal class SavedState : BaseSavedState {

        var menuItems = mutableListOf<MenuItem>()
        var selectedItemIndex: Int = 0

        constructor(superState: Parcelable) : super(superState)

        private constructor(`in`: Parcel) : super(`in`) {
            this.selectedItemIndex = `in`.readInt()
            `in`.readList(menuItems, ClassLoader.getSystemClassLoader())
        }

        override fun writeToParcel(out: Parcel, flags: Int) {
            super.writeToParcel(out, flags)
            out.writeInt(selectedItemIndex)
            out.writeList(menuItems)
        }
    }
}