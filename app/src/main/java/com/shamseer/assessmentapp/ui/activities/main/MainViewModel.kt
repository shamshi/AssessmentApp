package com.shamseer.assessmentapp.ui.activities.main

import androidx.lifecycle.MutableLiveData
import com.mikepenz.fastadapter.IItem
import com.shamseer.assessmentapp.data.remote.networking.models.Items
import com.shamseer.assessmentapp.ui.adapters.items.gallery.ItemGallery
import com.shamseer.assessmentapp.ui.base.BaseViewModel
import okhttp3.ResponseBody

/**
 * Created by Shamseer on 5/29/20.
 */

/** Gallery View Model
 * View Model to call API to download and fetch the item details from json file */
class MainViewModel : BaseViewModel() {

    // declare live data variable to show the items
    var showItems = MutableLiveData<ArrayList<IItem<*, *>>>()

    // declare live data variable to manage the json response
    var fetchItemInfo = MutableLiveData<ResponseBody>()

    /** initialize view model */
    fun initViewModel() {
        fetchItemInfo()
    }

    /** fetch item details */
    private fun fetchItemInfo() {
        showLoadingProgress()

        // call API to download file
        compositeDisposable.add(dataManager.downloadImageDetails()
            .compose(schedulerProvider.ioToMainFlowableScheduler())
            .subscribe({ response ->
                fetchItemInfo.value = response
            }, { error ->
                // Handle error
                handleError(error)
            }))
    }

    /** show gallery */
    fun showItemInfo(itemInfo: Items) {

        if(!itemInfo.items.isNullOrEmpty()) {
            val items: ArrayList<IItem<*, *>> = arrayListOf()
            itemInfo.items.map { item ->
                if (!item.imageUrl.isNullOrEmpty()) {
                    items.add(
                        ItemGallery() // adapter class
                            .withItem(item)
                            .withImage(item.imageUrl)
                    )
                }
            }

            showItems.value = items // update the live data variable to show the gallery
        }
    }
}