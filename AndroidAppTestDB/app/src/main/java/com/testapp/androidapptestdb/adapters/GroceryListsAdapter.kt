package com.testapp.androidapptestdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.testapp.androidapptestdb.R
import com.testapp.androidapptestdb.interfaces.ItemClickListener
import com.testapp.androidapptestdb.models.GroceryList
import com.testapp.androidapptestdb.viewholders.GroceryListVH

class GroceryListsAdapter(
    private val groceryList: ArrayList<GroceryList>,
    private val itemClickListener: ItemClickListener? = null
) :
    RecyclerView.Adapter<GroceryListVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryListVH {
        return GroceryListVH(
            mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.single_grocery_list_tile,
                parent,
                false
            ),
            itemClickListener = itemClickListener
        )
    }

    override fun onBindViewHolder(holder: GroceryListVH, position: Int) {
        holder.bind(source = groceryList[position], position = position)
    }

    override fun getItemCount(): Int {
        return groceryList.size
    }


}

