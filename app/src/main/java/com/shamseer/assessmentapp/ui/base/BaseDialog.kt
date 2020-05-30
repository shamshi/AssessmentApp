package com.shamseer.assessmentapp.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import butterknife.ButterKnife
import com.orhanobut.logger.Logger

/**
 * Created by Shamseer on 5/29/20.
 */

/** Base Dialog class to handle common functions */
abstract class BaseDialog : DialogFragment() {

    private var baseActivity: BaseActivity? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
        }
    }

    /** common function to whether the network is connected or not */
    fun isNetworkConnected(): Boolean {
        return if (baseActivity != null) {
            baseActivity!!.isNetworkConnected()
        } else false
    }

    /** common function to show toast */
    fun showToast(res: Int) {
        if (baseActivity != null) {
            baseActivity!!.showToast(res)
        }
    }

    /** common function to show toast */
    fun showToast(res: String) {
        if (baseActivity != null) {
            baseActivity!!.showToast(res)
        }
    }

    /** common function to create dialog */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val root = RelativeLayout(activity)
        root.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)

        val dialog = Dialog(context!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        if (dialog.window != null) {
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        dialog.setCanceledOnTouchOutside(true)
        return dialog
    }

    protected abstract fun bindViews(view: View)
    protected abstract fun setContentView(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(setContentView(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ButterKnife.bind(this, view)
        bindViews(view)
    }

    /** common function to show the network dialog */
    override fun show(fragmentManager: FragmentManager, tag: String?) {
        try {
            val transaction = fragmentManager.beginTransaction()
            val prevFragment = fragmentManager.findFragmentByTag(tag)
            if (prevFragment != null) {
                transaction.remove(prevFragment)
            }
            transaction.addToBackStack(null)
            show(transaction, tag)
        } catch (e: IllegalStateException) {
            Logger.d(e)
        }
    }

    /** common function to get called on network change */
    open fun onNetworkChange(isConnected: Boolean) {
        baseActivity!!.onNetworkChange()
    }
}