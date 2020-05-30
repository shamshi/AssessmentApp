package com.shamseer.assessmentapp.ui.adapters.items.details

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.mikepenz.fastadapter.items.AbstractItem
import com.shamseer.assessmentapp.R
import kotlinx.android.synthetic.main.item_details.view.*

/**
 * Created by Shamseer on 5/30/20.
 */

/** Adapter class to show the item details */
class ItemDetails : AbstractItem<ItemDetails, ItemDetails.DetailsViewHolder>() {

    var title: String? = null
    var description: String? = null
    var imageUrl: String? = null

    fun withTitle(title: String?): ItemDetails {
        this.title = title
        return this
    }

    fun withDescription(description: String?): ItemDetails {
        this.description = description
        return this
    }

    fun withImage(imageUrl: String?): ItemDetails {
        this.imageUrl = imageUrl
        return this
    }

    override fun getViewHolder(v: View): DetailsViewHolder {
        return DetailsViewHolder(v)
    }

    override fun getType(): Int {
        return R.id.details_item
    }

    override fun getLayoutRes(): Int {
        return R.layout.item_details
    }

    override fun bindView(holder: DetailsViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)

        val context = holder.itemView.context

        // Bind title
        holder.itemView.tvTitle.text = title

        // Bind description
        holder.itemView.tvDescription.text = description

        // Bind image
        if(!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .apply(RequestOptions().priority(Priority.HIGH).skipMemoryCache(true))
                .into(holder.itemView.ivItem)
        }
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}