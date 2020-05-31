package com.shamseer.assessmentapp.ui.activities.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.shamseer.assessmentapp.data.remote.networking.models.Items
import com.shamseer.assessmentapp.di.koin.modules.dataModule
import com.shamseer.assessmentapp.di.koin.modules.viewModelModule
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**
 * Created by Shamseer on 5/30/20.
 */
class DetailsViewModelTest {

    private lateinit var viewModel: DetailsViewModel

    private val item = Items.Item(1, "Title 1", "Description 1", "https://cloud.nousdigital.net/s/rezXHE6qGXGFHSd/preview")

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        stopKoin()
        startKoin {
            modules(listOf(viewModelModule, dataModule))
        }

        viewModel = DetailsViewModel()
    }

    @Test
    fun loadDetails_Items_ReturnsTrue() = runBlocking {
        viewModel.showItemDetails(item)
        val isItemNotEmpty = viewModel.showDetails.value
        assertNotNull(isItemNotEmpty)
        return@runBlocking
    }

    @Test
    fun isPresent_ItemDetails_ReturnsTrue() {
        viewModel.showItemDetails(item)
        assertTrue(viewModel.showDetails.value != null)
    }

    @After
    fun tearDown() {
    }
}