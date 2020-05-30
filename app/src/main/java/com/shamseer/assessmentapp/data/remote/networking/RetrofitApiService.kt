package com.shamseer.assessmentapp.data.remote.networking

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

/**
 * Created by Shamseer on 5/29/20.
 */

/** place to add all Retrofit Api Service functions */
interface RetrofitApiService {
    @Streaming // @Streaming will pass along the bytes right away instead of moving the entire file into memory
    @GET(ApiEndPoint.IMAGE_DETAILS_DOWNLOAD) // Get API end point to download json file
    fun downloadImageDetails(): Flowable<ResponseBody?>
}