package com.lounah.core.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.lounah.core.R
import com.lounah.core.extensions.getColorCompat
import com.lounah.core.extensions.hide
import com.lounah.core.extensions.show
import kotlinx.android.synthetic.main.view_connection_state.view.*

class ConnectionStateView : LinearLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_connection_state, this, true)
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        orientation = HORIZONTAL

        textViewConnectionState.apply {
            textSize = 14f
        }
    }

    fun applyConnectingState() {
        show()
        textViewConnectionState.textColorRes = context.getColorCompat(android.R.color.black)
        textViewConnectionState.text = context.resources.getString(R.string.connecting)
        progressBar.show()
    }

    fun applyNoConnectionState() {
        show()
        textViewConnectionState.textColorRes = context.getColorCompat(android.R.color.holo_red_light)
        textViewConnectionState.text = context.resources.getString(R.string.no_connection)
        progressBar.hide()
    }

    fun applyConnectedState() {
        hide()
    }
}