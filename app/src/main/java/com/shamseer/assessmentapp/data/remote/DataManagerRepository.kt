package com.shamseer.assessmentapp.data.remote

import com.shamseer.assessmentapp.data.remote.networking.ApiManagerRepository
import io.reactivex.Flowable
import okhttp3.ResponseBody

/**
 * Created by Shamseer on 5/29/20.
 */

/** class to communicate the view models to the network layer */
class DataManagerRepository(private val mApiManager: ApiManagerRepository) : DataManager {

    // function to call download json file API
    override fun downloadImageDetails(): Flowable<ResponseBody?> {
        return mApiManager.downloadImageDetails()
    }
}