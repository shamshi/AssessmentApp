package com.shamseer.assessmentapp.ui.activities.details

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.mikepenz.fastadapter.IItem
import com.shamseer.assessmentapp.applications.helpers.ktx.getObject
import com.shamseer.assessmentapp.data.remote.networking.models.Items
import com.shamseer.assessmentapp.ui.adapters.items.details.ItemDetails
import com.shamseer.assessmentapp.ui.base.BaseViewModel

/**
 * Created by Shamseer on 5/30/20.
 */

/** Item Details View Model
 * View Model to show the item details - full image, title and description */
class DetailsViewModel : BaseViewModel() {

    // declare live data variable to show the item details
    var showDetails = MutableLiveData<IItem<*, *>>()

    /** initialize view model */
    fun initViewModel(intent: Intent) {
        if (intent.getObject<Items.Item>() != null) {
            val item = intent.getObject<Items.Item>()
            if(item != null) {
                showItemDetails(item)
            }
        }
    }

    /** show Item Details */
    fun showItemDetails(item: Items.Item) {

        if(!item.imageUrl.isNullOrEmpty()) {
            val itemDetails = ItemDetails() // adapter class
                .withImage(item.imageUrl)
                .withTitle(item.title)
                .withDescription(item.description)

            showDetails.value = itemDetails // update the live data variable to show the details
        }
    }
}