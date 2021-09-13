package com.testapp.androidapptestdb.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.testapp.androidapptestdb.R
import com.testapp.androidapptestdb.adapters.GroceryListItemsAdapter
import com.testapp.androidapptestdb.base.BaseFragment
import com.testapp.androidapptestdb.databinding.FragmentCreateListBinding
import com.testapp.androidapptestdb.helpers.Constants
import com.testapp.androidapptestdb.helpers.RecyclerItemTouchHelper
import com.testapp.androidapptestdb.models.GroceryItem
import com.testapp.androidapptestdb.models.GroceryList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateListFragment : BaseFragment(), RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    private lateinit var mBinding: FragmentCreateListBinding

    private var groceryList = GroceryList()
    private var groceryListItems = ArrayList<GroceryItem>()
    private var allGroceryListItems = ArrayList<GroceryItem>()

    private lateinit var groceryListItemsAdapter: GroceryListItemsAdapter

    private var isFromEdit = false
    private var groceryListToEdit: GroceryList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            isFromEdit = it.getBoolean("isFromEdit", false)
            groceryListToEdit = it.getSerializable("groceryListToEdit") as GroceryList?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_list, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
        if (isFromEdit && groceryListToEdit != null) {
            mBinding.tvTitle.text = "Edit Grocery List"
            initData()
            enableSwipeToDelete()
        }
    }

    private fun initAdapter() {
        groceryListItemsAdapter = GroceryListItemsAdapter(
            groceryItemsList =
            groceryListItems
        )
        mBinding.rvItems.adapter = groceryListItemsAdapter
    }

    private fun initListeners() {
        mBinding.etItem.setImeActionLabel("Done", EditorInfo.IME_ACTION_DONE)
        mBinding.etItem.setOnEditorActionListener { _, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val itemToAdd = mBinding.etItem.text.toString().trim()
                if (itemToAdd.isNotEmpty()) {
                    groceryListItems.add(GroceryItem().apply {
                        id = System.currentTimeMillis().toInt()
                        name = mBinding.etItem.text.toString().trim()
                        createdAt = System.currentTimeMillis().toString()
                        updatedAt = System.currentTimeMillis().toString()
                        status = Constants.ITEM_PENDING
                    })
                    allGroceryListItems.add(groceryListItems[groceryListItems.size - 1])
                    groceryListItemsAdapter.notifyItemInserted(groceryListItems.size)
                    mBinding.etItem.setText("")
                    true
                } else
                    false

            } else
                false
        }

        mBinding.btnSave.setOnClickListener {

            if (validate()) {
                val type = object : TypeToken<ArrayList<GroceryItem>>() {}.type
                if (isFromEdit) {
                    val localUpdateAt = System.currentTimeMillis().toString()
                    groceryList.apply {
                        updatedAt = localUpdateAt
                        name = mBinding.etTitle.text.toString().trim()
                        allGroceryListItems.forEach {
                            it.updatedAt = localUpdateAt
                        }
                        status =
                            if (allGroceryListItems.any { it.status == Constants.ITEM_PENDING })
                                Constants.LIST_PENDING
                            else
                                Constants.LIST_COMPLETED
                        groceryItemsList.clear()
                        groceryItemsList = ArrayList()
                        groceryItemsList.addAll(allGroceryListItems)
                        items = Gson().toJson(groceryItemsList, type)
                    }
                } else {
                    groceryList.apply {
                        createdAt = System.currentTimeMillis().toString()
                        updatedAt = System.currentTimeMillis().toString()
                        name = mBinding.etTitle.text.toString().trim()
                        groceryItemsList.addAll(groceryListItems)
                        items = Gson().toJson(groceryItemsList, type)
                        status = Constants.LIST_PENDING
                    }
                }

                GlobalScope.launch(Dispatchers.IO) {

                    if (isFromEdit) {
                        if (baseActivity.initDataBase()
                                ?.updateGroceryList(groceryList = groceryList) == true
                        ) {
                            Log.d("ListUpdated", "Success")
                        } else {
                            Log.d("ListUpdated", "Failure")
                        }
                    } else {
                        if (baseActivity.initDataBase()
                                ?.addGroceryList(groceryList = groceryList) == true
                        ) {
                            Log.d("ListSaved", "Success")
                        } else {
                            Log.d("ListSaved", "Failure")
                        }
                    }

                    withContext(Dispatchers.Main) {
                        baseActivity.changeFragmentWithoutReCreation(
                            fragment = HomeFragment.newInstance(),
                            tagFragmentName = HomeFragment::class.java.canonicalName
                                ?: "HomeFragment"
                        )
                    }
                }
            }

        }
    }

    private fun validate(): Boolean {

        return when {
            mBinding.etTitle.text.toString().trim().isEmpty() -> {
                showToast(message = "Enter List Title")
                false
            }
            !isFromEdit && groceryListItems.isEmpty() -> {
                showToast(message = "Enter Items")
                false
            }
            else -> {
                true
            }
        }
    }

    private fun initData() {
        mBinding.btnSave.text = "update"
        mBinding.etTitle.setText(groceryListToEdit?.name ?: "")
        groceryList.apply {
            id = groceryListToEdit!!.id
            name = groceryListToEdit!!.name
            createdAt = groceryListToEdit!!.createdAt
            updatedAt = groceryListToEdit!!.updatedAt
            status = groceryListToEdit!!.status
            items = groceryListToEdit!!.items
            initItemsFromJson()
        }
        groceryListItems.addAll(groceryList.groceryItemsList)
        allGroceryListItems.addAll(groceryList.groceryItemsList)
        groceryListItemsAdapter.notifyDataSetChanged()
    }

    private fun enableSwipeToDelete() {
        val itemTouchHelper = RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this)
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(mBinding.rvItems)
    }

    companion object {
        @JvmStatic
        fun newInstance(isFromEdit: Boolean = false, groceryListToEdit: GroceryList? = null) =
            CreateListFragment().apply {
                arguments = Bundle().apply {
                    putBoolean("isFromEdit", isFromEdit)
                    putSerializable("groceryListToEdit", groceryListToEdit)
                }
            }
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int, position: Int) {
        groceryListItems[position].status = Constants.ITEM_DELETED
        allGroceryListItems[position].status = Constants.ITEM_DELETED
        groceryListItems.removeAt(position)
        groceryListItemsAdapter.notifyDataSetChanged()
    }
}