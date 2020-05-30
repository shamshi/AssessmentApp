package com.shamseer.assessmentapp.applications

import android.app.Application
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.shamseer.assessmentapp.BuildConfig
import com.shamseer.assessmentapp.di.koin.modules.dataModule
import com.shamseer.assessmentapp.di.koin.modules.viewModelModule
import okhttp3.OkHttpClient
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

/**
 * Created by Shamseer on 5/29/20.
 */

/** Base Application class to initialize common libraries */
class BaseApplication : Application() {

    private val mCalligraphyConfig: CalligraphyConfig by inject()

    override fun onCreate() {
        super.onCreate()

        // initialize Koin
        initKoin()

        // initialize Logger
        initLogger()

        // initialize Fast Network Library
        initNetwork()

        // initialize Fonts
        initFonts()
    }

    /** function to initialize Koin */
    fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(viewModelModule, dataModule))
        }
    }

    /** function to initialize Logger */
    private fun initLogger() {
        PrettyFormatStrategy.newBuilder().tag("LOGGER").build()
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }

    /** function to initialize initialize Fast Network Library */
    private fun initNetwork() {
        val client = OkHttpClient.Builder()
        AndroidNetworking.initialize(applicationContext, client.build())
        if (BuildConfig.DEBUG) { // Enable logger for debugging
            AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY)
        }
    }

    /** function to initialize initialize initialize Fonts */
    private fun initFonts() {
        CalligraphyConfig.initDefault(mCalligraphyConfig)
    }
}