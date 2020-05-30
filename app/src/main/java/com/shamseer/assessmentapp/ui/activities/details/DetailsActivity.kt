package com.shamseer.assessmentapp.ui.activities.details

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.IItem
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.applications.helpers.email.SendEmail
import com.shamseer.assessmentapp.ui.adapters.items.details.ItemDetails
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.content_details.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.IOException

/**
 * Created by Shamseer on 5/30/20.
 */

/** Item Details Activity
  * Activity to show the full image, title and description
  * Also have an option to send the above details by email */
class DetailsActivity : SendEmail() { // SendEmail - Base class to handle the functions to download the image and send email

    // Initialize fast item adapter
    var fastItemAdapter = FastItemAdapter<IItem<*, *>>()

    // Initialize view model
    private val viewModel: DetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        setTitle(R.string.title_activity_details)

        // Show Back button
        if(supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        // Setup recycler view
        rvDetails.layoutManager = LinearLayoutManager(this@DetailsActivity)
        rvDetails.itemAnimator = DefaultItemAnimator()
        rvDetails.adapter = fastItemAdapter
        if(rvDetails.itemAnimator != null) {
            rvDetails.itemAnimator!!.changeDuration = 0
        }

        fastItemAdapter.saveInstanceState(savedInstanceState)

        viewModel.initViewModel(intent)

        showDetails()
        showProgressLoading()
        handleError()

        // Download image and send email
        fabEmail.setOnClickListener {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    checkForPermission()
                } else {
                    saveImage()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /** Show Image, Title and Description */
    private fun showDetails() {
        viewModel.showDetails.observe(this, Observer { item ->
            if (item != null) {
                if(!(item as ItemDetails).title.isNullOrEmpty()) {
                    emailSubject = item.title!!
                }
                if(!item.description.isNullOrEmpty()) {
                    emailMessage = item.description!!
                }
                if(!item.imageUrl.isNullOrEmpty()) {
                    // Call base class function to download image
                    downloadImage(item.imageUrl!!)
                }
                val items: ArrayList<IItem<*, *>> = arrayListOf()
                items.add(item)
                progressLayout.showContent()
                fastItemAdapter.clear()
                fastItemAdapter.setNewList(items)
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

    /** Handle back button click */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /** Check for write permission */
    private fun checkForPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            // request permission
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION
            )
        } else {
            // Call base class function to save image and send email
            saveImage()
        }
    }

    /** Handle permission request */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    saveImage()
                } else {
                    showToast(R.string.error_save_image_permission_required) // Call base class function to show toast message
                }
                return
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        completableJob.cancel() // cancel background job to write image on disc
    }

    companion object {
        const val REQUEST_STORAGE_PERMISSION = 1000
    }
}
