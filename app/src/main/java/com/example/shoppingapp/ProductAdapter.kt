package com.example.shoppingapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item.*

// in RecyclerViewAdapter, whenever the changes are made, the whole
// list is created again and notifyDataSetChanged() method is called which is not optimized

// to avoid the above condition, we use ListAdapter b/c it used a DiffUtil interface which compares
// the previous list with the new list and make the changes without creating the whole list again
// and again. This method is more optimized and should be used.
class ProductAdapter(val listener: (Product) -> Unit):
        ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallBack()) {


    // LayoutContainer interface is implemented here to directly access the id's of xml files
    // override val containerView is the method of LayoutContainer interface
    // in "list_item". This feature is experimental and required to change build.gradle file
    inner class ViewHolder (override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {
        init {
            // itemView is an inbuilt button to handle click on particular item
            itemView.setOnClickListener {
                // Product List is encapsulated by ListAdapter
                // getItem is an inbuilt method of ListAdapter which
                listener(getItem(adapterPosition))
            }
        }

        // here we can directly use the id's of xml files b/c of LayoutContainer interface
        @SuppressLint("SetTextI18n")
        fun bind(productData: Product) {
            productData.apply {
                img_product_image.setImageResource(imageId)
                txt_name.text = name
                txt_price.text = "Price: Rs $price"
                txt_description.text = shortDescription
                txt_rating.text = "Rating: $rating"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
            parent, false)

        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

// DiffUtil has two functions to be overridden i.e. areItemsTheSame() and areContentsTheSame()
class DiffCallBack: DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }

}
