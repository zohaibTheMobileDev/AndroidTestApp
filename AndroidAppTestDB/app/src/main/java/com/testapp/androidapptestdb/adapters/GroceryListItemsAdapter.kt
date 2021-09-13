package com.testapp.androidapptestdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.testapp.androidapptestdb.R
import com.testapp.androidapptestdb.interfaces.ItemClickListener
import com.testapp.androidapptestdb.models.GroceryItem
import com.testapp.androidapptestdb.viewholders.GroceryListItemVH

class GroceryListItemsAdapter(
    private val groceryItemsList: ArrayList<GroceryItem>,
    private val itemClickListener: ItemClickListener? = null
) :
    RecyclerView.Adapter<GroceryListItemVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryListItemVH {
        return GroceryListItemVH(
            mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.single_grocery_list_item_tile,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GroceryListItemVH, position: Int) {
        holder.bind(source = groceryItemsList[position], position = position)
    }

    override fun getItemCount(): Int {
        return groceryItemsList.size
    }


}