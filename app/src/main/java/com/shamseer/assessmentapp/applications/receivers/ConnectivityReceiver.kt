package com.shamseer.assessmentapp.applications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager

/**
 * Created by Shamseer on 5/29/20.
 */

/** Connectivity Receiver class to manage Intent broadcast */
class ConnectivityReceiver : BroadcastReceiver() {

    private var connectivityReceiverListener: ConnectivityReceiverListener? = null

    fun setConnectivityReceiverListener(connectivityReceiverListener: ConnectivityReceiverListener) {
        this.connectivityReceiverListener = connectivityReceiverListener
    }

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context, arg1: Intent) {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected)
        }
    }

    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }
}