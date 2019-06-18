package com.lounah.core.network.connectivitychecker

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.lounah.core.logger.Logger

class ConnectionCheckerDelegate(private val context: Context) {

    enum class ConnectivityState {
        CONNECTED, CONNECTING, NO_CONNECTION
    }

    interface OnConnectionStateChangedCallback {
        fun onConnectionStateChanged(newState: ConnectivityState)
    }

    private companion object {
        private const val ACTION_CONNECTIVITY_CHANGE = "ConnectivityChange"
    }

    private val networkStateReceiver: ConnectionStateReceiver = ConnectionStateReceiver {
        isCurrentlyConnected = it == ConnectivityState.CONNECTED
        connectionStateChangeListener?.onConnectionStateChanged(it)
    }

    private var connectionStateChangeListener: OnConnectionStateChangedCallback? = null

    private var connectionStateMonitor: ConnectionStateMonitor? = null

    private var isCurrentlyConnected: Boolean = false

    fun registerConnectivityStateListener(connectionStateChangeListener: OnConnectionStateChangedCallback) {
        val intentFilter = IntentFilter()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            createChangeConnectivityMonitor()
            intentFilter.addAction(ACTION_CONNECTIVITY_CHANGE)
        } else {
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        }
        context.registerReceiver(networkStateReceiver, intentFilter)

        this.connectionStateChangeListener = connectionStateChangeListener
    }

    fun unregisterConnectivityStateListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            try {
                connectivityManager?.unregisterNetworkCallback(connectionStateMonitor)
            } catch (ex: Exception) {
                Logger.e(ex)
                /** случается, если мы пытаемся удалить еще не созданный коллбек */
                /** или, например, пытаемся удалить уже удаленный коллбек (do not keep activities workaround) */
                /** do nothing */
            }
        }
        try {
            context.unregisterReceiver(networkStateReceiver)
        } catch (wasNotRegistered: IllegalArgumentException) {
            Logger.e(wasNotRegistered)
        }

        connectionStateChangeListener = null
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun createChangeConnectivityMonitor() {
        val intent = Intent(ACTION_CONNECTIVITY_CHANGE)
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectionStateMonitor = ConnectionStateMonitor(context, intent)
        try {
            connectivityManager?.registerDefaultNetworkCallback(connectionStateMonitor)
        } catch (npe: NullPointerException) {
            Logger.e(npe)
            /** случается, если мы пытаемся зарегистрировать еще не созданный коллбек */
            /** do nothing */
        }
    }
}