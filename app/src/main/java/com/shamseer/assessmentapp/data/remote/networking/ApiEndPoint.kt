package com.shamseer.assessmentapp.data.remote.networking

import com.shamseer.assessmentapp.BuildConfig

/**
 * Created by Shamseer on 5/29/20.
 */

/** place to manage all the API end points */
object ApiEndPoint {

    // Base API url
    private const val BASE_URL: String = BuildConfig.SERVER_URL

    // Url to download json file
    const val IMAGE_DETAILS_DOWNLOAD = "${BASE_URL}s/Njedq4WpjWz4KKk/download"
}