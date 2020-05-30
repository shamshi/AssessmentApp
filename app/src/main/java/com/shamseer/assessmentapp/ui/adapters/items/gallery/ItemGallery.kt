package com.shamseer.assessmentapp.ui.adapters.items.gallery

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.mikepenz.fastadapter.items.AbstractItem
import com.shamseer.assessmentapp.R
import com.shamseer.assessmentapp.data.remote.networking.models.Items
import kotlinx.android.synthetic.main.item_gallery.view.*

/**
 * Created by Shamseer on 5/29/20.
 */

/** Adapter class to show the item gallery */
class ItemGallery : AbstractItem<ItemGallery, ItemGallery.GalleryViewHolder>() {

    var item: Items.Item? = null
    var imageUrl: String? = null

    fun withItem(item: Items.Item): ItemGallery {
        this.item = item
        return this
    }

    fun withImage(imageUrl: String?): ItemGallery {
        this.imageUrl = imageUrl
        return this
    }

    override fun getViewHolder(v: View): GalleryViewHolder {
        return GalleryViewHolder(v)
    }

    override fun getType(): Int {
        return R.id.gallery_item
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_gallery
    }

    override fun bindView(holder: GalleryViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)

        val context = holder.itemView.context

        // Bind image
        if(!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions().priority(Priority.HIGH).skipMemoryCache(true))
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.itemView.ivItem)
        }
    }

    class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}