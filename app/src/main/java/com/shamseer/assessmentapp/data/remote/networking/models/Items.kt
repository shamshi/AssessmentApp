package com.shamseer.assessmentapp.data.remote.networking.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Shamseer on 5/29/20.
 */

/** model to retrieve items from json response */
data class Items(
    @SerializedName("items") val items: List<Item>? // Items model
) : Serializable {

    data class Item( // Item model
        @SerializedName("id") val id: Int?,
        @SerializedName("title") val title: String?,
        @SerializedName("description") val description: String?,
        @SerializedName("imageUrl") val imageUrl: String?
    ) : Serializable
}
