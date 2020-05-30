package com.shamseer.assessmentapp.applications.helpers.network

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Shamseer on 5/29/20.
 */

/** object to check whether the network connection is available or not  */
object NetworkUtils {

    // To check whether the network connection is available or not
    @Suppress("DEPRECATION")
    fun isNetworkConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}