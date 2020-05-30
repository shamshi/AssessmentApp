package com.shamseer.assessmentapp.data.remote.networking

import io.reactivex.Flowable
import okhttp3.ResponseBody

/**
 * Created by Shamseer on 5/29/20.
 */

/** interface to represent all API functions */
interface ApiManager {

    // function to call download json file API
    fun downloadImageDetails(): Flowable<ResponseBody?>
}