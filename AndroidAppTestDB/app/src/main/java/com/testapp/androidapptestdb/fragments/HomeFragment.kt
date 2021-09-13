package com.testapp.androidapptestdb.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.testapp.androidapptestdb.R
import com.testapp.androidapptestdb.adapters.GroceryListsAdapter
import com.testapp.androidapptestdb.base.BaseFragment
import com.testapp.androidapptestdb.databinding.FragmentHomeBinding
import com.testapp.androidapptestdb.helpers.Constants
import com.testapp.androidapptestdb.helpers.hideView
import com.testapp.androidapptestdb.helpers.showView
import com.testapp.androidapptestdb.interfaces.ItemClickListener
import com.testapp.androidapptestdb.models.GroceryList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment : BaseFragment(), ItemClickListener {

    private lateinit var mBinding: FragmentHomeBinding

    private var groceryLists = ArrayList<GroceryList>()
    private lateinit var groceryListAdapter: GroceryListsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initGroceryList()
    }

    private fun initAdapter() {
        groceryListAdapter = GroceryListsAdapter(
            groceryList = groceryLists,
            itemClickListener = this
        )
        mBinding.rvGroceryLists.adapter = groceryListAdapter
    }

    private fun initGroceryList() {
        GlobalScope.launch(Dispatchers.IO) {
            groceryLists.clear()
            groceryLists.addAll(
                baseActivity.initDataBase()?.getAllGroceryLists() ?: arrayListOf()
            )
            withContext(Dispatchers.Main) {
                toggleEmptyData()
                groceryListAdapter.notifyDataSetChanged()
            }

        }
    }

    private fun toggleEmptyData() {
        if (groceryLists.isEmpty())
            mBinding.tvNoDataFound.showView()
        else
            mBinding.tvNoDataFound.hideView()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun itemTapped(position: Int) {
        baseActivity.replaceFragmentWithNoBackStack(
            targetFragment = CreateListFragment.newInstance(
                groceryListToEdit =
                groceryLists[position],
                isFromEdit = true
            ),
            tagName = CreateListFragment::class.java.canonicalName ?: "CreateListFragment"
        )
    }
}