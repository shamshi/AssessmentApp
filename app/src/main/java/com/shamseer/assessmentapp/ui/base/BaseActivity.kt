package com.shamseer.assessmentapp.ui.base

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.applications.helpers.network.NetworkUtils
import com.shamseer.assessmentapp.applications.receivers.ConnectivityReceiver
import com.shamseer.assessmentapp.ui.dialogs.network.NetworkDialog

/**
 * Created by Shamseer on 5/29/20.
 */

/** Base Activity class to handle common functions */
abstract class BaseActivity : AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    private var isRunning: Boolean = false
    private val dialog = NetworkDialog.newInstance()
    private var connectivityReceiver: ConnectivityReceiver? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initConnectivity()
    }

    /** function to check the network connectivity */
    private fun initConnectivity() {
        connectivityReceiver = ConnectivityReceiver()
        connectivityReceiver!!.setConnectivityReceiverListener(this)
        val connectivityIntentFilter = IntentFilter()
        connectivityIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(connectivityReceiver, connectivityIntentFilter)
    }

    /** common function to start activity */
    override fun startActivity(intent: Intent) {
        try {
            super.startActivity(intent)
            overridePendingTransition(R.anim.anim_open_enter, R.anim.anim_open_exit)
        } catch (e: android.content.ActivityNotFoundException) {
            Logger.d(e)
        }
    }

    /** common function to show toast */
    fun showToast(text: String) {
        val toast = Toast(this)
        val view = LayoutInflater.from(this).inflate(R.layout.layout_toast, null)
        val textMessage = view.findViewById<TextView>(R.id.tvMessage)
        textMessage.text = text
        toast.view = view
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }

    /** common function to show toast */
    fun showToast(message: Int) {
        showToast(getString(message))
    }

    /** common function to handle the network connection */
    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        try {
            if (isRunning) {
                if (!isConnected) {
                    dialog.show(supportFragmentManager, "NetworkDialog")
                } else {
                    if (NetworkDialog.isShowing && dialog.isAdded) {
                        dialog.dismiss()
                        recreate()
                    }
                }
            }
        } catch (e: java.lang.NullPointerException) {
            Logger.d(e)
        } catch (e: IllegalStateException) {
            Logger.d(e)
        }
    }

    /** common function to whether the network is connected or not */
    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(this)
    }

    /** common function to get called on network change */
    fun onNetworkChange() {}

    /** common function to handle onBackPressed */
    override fun onBackPressed() {
        try {
            super.onBackPressed()
            overridePendingTransition(R.anim.anim_close_enter, R.anim.anim_close_exit)
        } catch (e: java.lang.NullPointerException) {
            Logger.d(e)
        }
    }

    /** common function to handle onResume */
    override fun onResume() {
        try {
            super.onResume()
            isRunning = true
        } catch (e: java.lang.IllegalStateException) {
            Logger.d(e)
        }
    }

    /** common function to handle onPause */
    override fun onPause() {
        try {
            super.onPause()
            isRunning = false
        } catch (e: java.lang.IllegalStateException) {
            Logger.d(e)
        }
    }

    /** common function to handle onDestroy */
    public override fun onDestroy() {
        if (connectivityReceiver != null) {
            unregisterReceiver(connectivityReceiver)
        }
        super.onDestroy()
    }

}