package com.lounah.core.network.connectivitychecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo

class ConnectionStateReceiver(
    private val networkConnectedCallback: (ConnectionCheckerDelegate.ConnectivityState) -> Unit = {}
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            networkConnectedCallback.invoke(ConnectionCheckerDelegate.ConnectivityState.CONNECTED)
        } else {
            networkConnectedCallback.invoke(ConnectionCheckerDelegate.ConnectivityState.NO_CONNECTION)
        }
    }
}