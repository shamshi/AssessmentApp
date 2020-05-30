package com.shamseer.assessmentapp.ui.activities.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.mikepenz.fastadapter.IAdapter
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.listeners.OnClickListener
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.applications.helpers.ktx.gson
import com.shamseer.assessmentapp.applications.helpers.reader.FileReader
import com.shamseer.assessmentapp.data.remote.networking.models.Items
import com.shamseer.assessmentapp.ui.activities.details.DetailsActivity
import com.shamseer.assessmentapp.ui.adapters.items.gallery.ItemGallery
import com.shamseer.assessmentapp.ui.base.BaseActivity
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream

/**
 * Created by Shamseer on 5/29/20.
 */

/** Gallery Activity
 * Activity to show the image gallery */
class MainActivity : BaseActivity(), OnClickListener<IItem<*, *>>, FileReader.ReadHelper { // FileReader.ReadHelper - helper class to read the downloaded json file

    // Initialize fast item adapter
    var fastItemAdapter = FastItemAdapter<IItem<*, *>>()

    // Initialize view model
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle(R.string.title_activity_gallery)

        // Setup recycler view
        rvItems.apply {
            rvItems.adapter = fastItemAdapter.withOnClickListener(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            rvItems.layoutManager = GridLayoutManager(this@MainActivity, 3)
        }
        fastItemAdapter.saveInstanceState(savedInstanceState)

        viewModel.initViewModel()

        fetchItemInfo()
        showItems()
        showProgressLoading()
        handleError()
    }

    /** function to convert the jsn response to the Items model */
    override fun showItemInfo(buffer: ByteArrayOutputStream) {
        val itemInfo = gson.fromJson(buffer.toString(), Items::class.java)
        viewModel.showItemInfo(itemInfo) // call view model function to show the gallery
    }

    /** function to call helper class function to read the json response */
    private fun fetchItemInfo() {
        viewModel.fetchItemInfo.observe(this, Observer { responseBody ->
            if (responseBody != null) {
                // helper class function to read the json response
                FileReader(this@MainActivity, this@MainActivity, responseBody).execute()
            }
        })
    }

    /** show gallery items */
    private fun showItems() {
        viewModel.showItems.observe(this, Observer {
            if (it != null && it.size > 0) {
                progressLayout.showContent()
                fastItemAdapter.clear()
                fastItemAdapter.setNewList(it)
            }
        })
    }

    /** Show / Hide progress loader */
    private fun showProgressLoading() {
        viewModel.showProgress.observe(this, Observer {
            if (progressLayout != null) {
                if (it) {
                    progressLayout.showLoading()
                } else {
                    progressLayout.showContent()
                }
            }
        })
    }

    /** Handle error */
    private fun handleError() {
        viewModel.handleError.observe(this, Observer {
            if (viewModel.handleError.value != null) {
                showToast(viewModel.handleError.value!!)
            }
        })
    }

    /** Navigate to the item details activity by passing the selected item */
    override fun onClick(v: View?, adapter: IAdapter<IItem<*, *>>?, item: IItem<*, *>?, position: Int): Boolean {
        if (item is ItemGallery) {
            val intent = Intent(this@MainActivity, DetailsActivity::class.java).putExtra(Items.Item::class.java.simpleName, item.item)
            startActivity(intent)
        }
        return true
    }
}
