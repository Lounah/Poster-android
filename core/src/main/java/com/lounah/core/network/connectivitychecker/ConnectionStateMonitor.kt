package com.lounah.core.network.connectivitychecker

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class ConnectionStateMonitor(private val context: Context, private val intent: Intent)
    : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network?) {
        context.sendBroadcast(intent)
    }
    override fun onLost(network: Network?) {
        context.sendBroadcast(intent)
    }
}