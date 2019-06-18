package com.lounah.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lounah.core.extensions.hide
import com.lounah.core.extensions.show

class EmptySupportingRecyclerView : RecyclerView {

    interface OnTranslationYChangedCallback {
        fun onTranslationYChanged(translationY: Float)
    }

    var emptyView: View? = null
        set(view) {
            field = view
            checkIfEmpty()
        }
    /**
     * Слушатель изменения видмости [EmptyRecyclerView.emptyView].
     * Вызывается, когда видимость [EmptyRecyclerView.emptyView] меняется.
     *
     * @param emptyViewVisibility значение видмости [EmptyRecyclerView.emptyView]. Может быть либо [View.VISIBLE]
     * либо [View.GONE]
     */
    var onEmptyViewStateChangeListener: (emptyViewVisibility: Int) -> Unit = {}

    private val observer = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle)

    internal fun checkIfEmpty() {
        val emptyView = this.emptyView
        val adapter = this.adapter
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter.itemCount == 0
            if (emptyViewVisible) {
                hide()
                emptyView.show()
            } else {
                show()
                emptyView.hide()
            }
            onEmptyViewStateChangeListener(emptyView.visibility)
        }
    }

    override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)
        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }
}