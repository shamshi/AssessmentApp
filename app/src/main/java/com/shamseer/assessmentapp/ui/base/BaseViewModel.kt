package com.shamseer.assessmentapp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orhanobut.logger.Logger
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.applications.helpers.rx.AppSchedulerProvider
import com.shamseer.assessmentapp.data.remote.DataManagerRepository
import io.reactivex.disposables.CompositeDisposable
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Shamseer on 5/29/20.
 */

/** Base View Model class to handle common view model functions */
open class BaseViewModel : ViewModel(), KoinComponent {

    val dataManager: DataManagerRepository by inject() // inject dataManager
    val schedulerProvider: AppSchedulerProvider by inject() // inject schedulerProvider
    val compositeDisposable: CompositeDisposable by inject() // inject compositeDisposable

    // declare common live data variable to Show / Hide progress bar
    val showProgress = MutableLiveData<Boolean>()

    /** common function to show loading */
    fun showLoadingProgress() {
        showProgress.value = true
    }

    /** common function to hide loading */
    fun hideLoadingProgress() {
        showProgress.value = false
    }

    /** common function to handle error */
    val handleError = MutableLiveData<String>()

    /** common function to show message */
    val showMessage = MutableLiveData<String>()

    /** common function to handle error */
    fun handleError(e: Throwable) {
        Logger.d(e)
        handleError.value = (R.string.error_api_call_failure).toString()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose() // dispose compositeDisposable
    }
}