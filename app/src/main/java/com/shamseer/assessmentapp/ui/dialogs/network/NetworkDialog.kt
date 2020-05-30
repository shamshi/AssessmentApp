package com.shamseer.assessmentapp.ui.dialogs.network

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.ui.base.BaseDialog
import com.shamseer.assessmentapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.dialog_network.*

/**
 * Created by Shamseer on 5/29/20.
 */

/** Network Dialog class to handle network related operations */
class NetworkDialog : BaseDialog() {

    companion object {
        var isShowing = false
        fun newInstance(): NetworkDialog {
            return NetworkDialog()
        }
    }

    override fun bindViews(view: View) {
        btnRetry.setOnClickListener {
            if (isNetworkConnected()) {
                dismiss()
                startActivity(Intent(context!!, BaseActivity::class.java))
            }
        }
        isShowing = true
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        isShowing = false
    }

    override fun setContentView(): Int {
        return R.layout.dialog_network
    }

    override fun onNetworkChange(isConnected: Boolean) {
        super.onNetworkChange(isConnected)
        if (isConnected) {
            dismiss()
        }
    }
}