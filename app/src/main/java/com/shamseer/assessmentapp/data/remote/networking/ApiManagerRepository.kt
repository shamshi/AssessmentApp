package com.shamseer.assessmentapp.data.remote.networking

import io.reactivex.Flowable
import okhttp3.ResponseBody
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 * Created by Shamseer on 5/29/20.
 */

/** class to represent all API functions */
class ApiManagerRepository() : RetrofitApiService, KoinComponent {

    // inject Retrofit Api Service
    private val mRetrofitApiManager: RetrofitApiService by inject()

    // function to call download json file API
    override fun downloadImageDetails(): Flowable<ResponseBody?> {
        return mRetrofitApiManager.downloadImageDetails()
    }
}