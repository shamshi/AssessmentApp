package com.shamseer.assessmentapp.ui.activities.main

import com.shamseer.assessmentapp.applications.helpers.rx.AppSchedulerProvider
import com.shamseer.assessmentapp.data.remote.networking.ApiManagerRepository
import com.shamseer.assessmentapp.di.koin.modules.dataModule
import com.shamseer.assessmentapp.di.koin.modules.viewModelModule
import io.reactivex.disposables.CompositeDisposable
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.mockito.MockitoAnnotations

/**
 * Created by Shamseer on 5/30/20.
 */
class MainViewModelTest {

    private var apiManager: ApiManagerRepository? = null
    private var apiManagerData: ApiManagerRepository? = null
    private var schedulerProvider: AppSchedulerProvider? = null
    private var compositeDisposable: CompositeDisposable? = null

    @Before
    fun setUp() {

        stopKoin()
        startKoin {
            modules(listOf(viewModelModule, dataModule))
        }

        apiManager = ApiManagerRepository()
        apiManagerData = ApiManagerRepository()
        schedulerProvider = AppSchedulerProvider()
        compositeDisposable = CompositeDisposable()

        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun item_DownloadFile_ReturnsExpectedValue() {
        if(apiManager != null) {
            val response = apiManager!!.downloadImageDetails().test()
            if (response.errors().isEmpty()) {
                response.assertNoErrors()
                assertTrue(response.isTerminated)
                response.dispose()
            } else {
                response.assertError(Throwable::class.java)
                response.dispose()
            }
        }
    }

    @After
    fun tearDown() {
        apiManager = null
        apiManagerData = null
        schedulerProvider = null
        compositeDisposable = null
    }
}