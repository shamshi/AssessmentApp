package com.shamseer.assessmentapp.applications.helpers.rx

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Shamseer on 5/29/20.
 */

/** class to manage Android Schedulers */
class AppSchedulerProvider : SchedulerProvider {

    /** executes actions on the Android main thread */
    override fun ui(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    /** used for asynchronously performing blocking IO */
    override fun io(): Scheduler {
        return Schedulers.io()
    }

    /** Applies a function to the upstream Flowable and returns a Publisher with optionally different element type */
    override fun <T> ioToMainFlowableScheduler(): FlowableTransformer<T, T> = FlowableTransformer { upstream ->
        upstream.subscribeOn(io())
            .observeOn(ui()).onBackpressureBuffer()
    }
}