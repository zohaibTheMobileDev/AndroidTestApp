package com.testapp.androidapptestdb.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.testapp.androidapptestdb.databinding.SingleGroceryListTileBinding
import com.testapp.androidapptestdb.interfaces.ItemClickListener
import com.testapp.androidapptestdb.models.GroceryList

class GroceryListVH(
    private val mBinding: SingleGroceryListTileBinding,
    private val itemClickListener: ItemClickListener? = null
) :
    RecyclerView.ViewHolder(mBinding.root) {

    fun bind(source: GroceryList, position: Int) {
        mBinding.source = source
        mBinding.position = position
        mBinding.listener = itemClickListener
    }
}