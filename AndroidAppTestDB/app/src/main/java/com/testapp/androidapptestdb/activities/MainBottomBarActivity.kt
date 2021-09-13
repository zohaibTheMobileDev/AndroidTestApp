package com.testapp.androidapptestdb.activities

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.testapp.androidapptestdb.R
import com.testapp.androidapptestdb.base.BaseActivity
import com.testapp.androidapptestdb.databinding.ActivityMainBottomBarBinding
import com.testapp.androidapptestdb.fragments.AllListsFragment
import com.testapp.androidapptestdb.fragments.CreateListFragment
import com.testapp.androidapptestdb.fragments.HomeFragment

class MainBottomBarActivity : BaseActivity() {

    private lateinit var mBinding: ActivityMainBottomBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_bottom_bar)
        initClickListeners()
        changeFragmentWithoutReCreation(
            fragment = HomeFragment.newInstance(),
            tagFragmentName = HomeFragment::class.java.canonicalName ?: "HomeFragment"
        )
    }

    private fun initClickListeners() {

        mBinding.tabOne.setOnClickListener {
            changeFragmentWithoutReCreation(
                fragment = HomeFragment.newInstance(),
                tagFragmentName = HomeFragment::class.java.canonicalName ?: "HomeFragment"
            )
        }

        mBinding.tabTwo.setOnClickListener {
            changeFragmentWithoutReCreation(
                fragment = AllListsFragment.newInstance(),
                tagFragmentName = AllListsFragment::class.java.canonicalName ?: "AllListsFragment"
            )
        }

        mBinding.btnAdd.setOnClickListener {
            replaceFragmentWithNoBackStack(
                targetFragment = CreateListFragment.newInstance(),
                tagName = CreateListFragment::class.java.canonicalName ?: "CreateListFragment"
            )
        }
    }
}