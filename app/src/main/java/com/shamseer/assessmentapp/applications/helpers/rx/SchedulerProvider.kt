package com.shamseer.assessmentapp.applications.helpers.rx

import io.reactivex.*

/** interface to manager Android Schedulers */
interface SchedulerProvider {

    /** executes actions on the Android main thread */
    fun ui(): Scheduler

    /** used for asynchronously performing blocking IO */
    fun io(): Scheduler

    /** Applies a function to the upstream Flowable and returns a Publisher with optionally different element type */
    fun <T> ioToMainFlowableScheduler(): FlowableTransformer<T, T>
}