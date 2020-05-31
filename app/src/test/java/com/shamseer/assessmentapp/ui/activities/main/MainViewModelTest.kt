package com.shamseer.assessmentapp.ui.activities.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.shamseer.assessmentapp.data.remote.networking.models.Items
import com.shamseer.assessmentapp.di.koin.modules.dataModule
import com.shamseer.assessmentapp.di.koin.modules.viewModelModule
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**
 * Created by Shamseer on 5/30/20.
 */
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    private val items = Items(listOf(
        Items.Item(id = 1, title = "title 1", description = "description 1", imageUrl = "https://cloud.nousdigital.net/s/rezXHE6qGXGFHSd/preview"),
        Items.Item(id = 2, title = "title 2", description = "description 1", imageUrl = "https://cloud.nousdigital.net/s/rFDbXBoyiHEmMfs/preview")
    ))

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {

        stopKoin()
        startKoin {
            modules(listOf(viewModelModule, dataModule))
        }

        viewModel = MainViewModel()
    }

    @Test
    fun loadGallery_Items_ReturnsTrue() = runBlocking {
        viewModel.showItemInfo(items)
        val isItemNotEmpty = viewModel.showItems.value
        Assert.assertNotNull(isItemNotEmpty)
        return@runBlocking
    }

    @Test
    fun isPresent_Gallery_ReturnsTrue() {
        viewModel.showItemInfo(items)
        Assert.assertTrue(viewModel.showItems.value != null)
    }

    @After
    fun tearDown() {
    }
}