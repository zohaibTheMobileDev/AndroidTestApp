package com.testapp.androidapptestdb.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.testapp.androidapptestdb.databinding.SingleGroceryListItemTileBinding
import com.testapp.androidapptestdb.models.GroceryItem

class GroceryListItemVH(private val mBinding: SingleGroceryListItemTileBinding) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(source: GroceryItem, position: Int) {
        mBinding.source = source
        mBinding.position = position
    }
}