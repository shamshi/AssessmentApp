package com.shamseer.assessmentapp.di.koin.modules

import android.content.Context
import com.google.gson.Gson
import com.shamseer.assessmentapp.BuildConfig
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.applications.helpers.rx.AppSchedulerProvider
import com.shamseer.assessmentapp.data.remote.DataManagerRepository
import com.shamseer.assessmentapp.data.remote.networking.ApiManagerRepository
import com.shamseer.assessmentapp.data.remote.networking.RetrofitApiService
import io.reactivex.disposables.CompositeDisposable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.util.concurrent.TimeUnit

/**
 * Created by Shamseer on 5/29/20.
 */

/** Koin dependency injection framework
  * Data Module declaration, a space to gather all components definitions */
val dataModule = module {

    // declare AppSchedulerProvider
    // singleton definition of given type. Koin keeps only one instance of this definition
    single {
        AppSchedulerProvider()
    }

    // declare CompositeDisposable
    // factory definition of given type. Koin gives a new instance each time
    factory {
        CompositeDisposable()
    }

    // declare CalligraphyConfig
    // singleton definition of given type. Koin keeps only one instance of this definition
    single {
        CalligraphyConfig.Builder()
            .setDefaultFontPath((get() as Context).getString(R.string.regular))
            .setFontAttrId(R.attr.fontPath)
            .build()
    }

    // declare DataManagerRepository
    // singleton definition of given type. Koin keeps only one instance of this definition
    single {
        DataManagerRepository(get())
    }

    // declare Gson
    // singleton definition of given type. Koin keeps only one instance of this definition
    single {
        Gson()
    }

    // declare ApiManager
    // singleton definition of given type. Koin keeps only one instance of this definition
    single {
        ApiManagerRepository()
    }

    // declare Retrofit
    // singleton definition of given type. Koin keeps only one instance of this definition
    single {
        val client = OkHttpClient.Builder()

        client.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val request =
                original.newBuilder()
                    .method(original.method(), original.body()).build()

            return@Interceptor chain.proceed(request)
        })

        client.connectTimeout(if (BuildConfig.DEBUG) 30 else 10, TimeUnit.SECONDS)
            .readTimeout(if (BuildConfig.DEBUG) 30 else 10, TimeUnit.SECONDS)
            .writeTimeout(if (BuildConfig.DEBUG) 30 else 10, TimeUnit.SECONDS).build()
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.SERVER_URL).client(client.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()

        retrofit.create(RetrofitApiService::class.java)
    }
}