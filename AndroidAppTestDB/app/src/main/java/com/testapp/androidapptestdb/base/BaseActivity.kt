package com.testapp.androidapptestdb.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.testapp.androidapptestdb.R
import com.testapp.androidapptestdb.helpers.AppDataBase

open class BaseActivity : AppCompatActivity() {

    private var appDataBase: AppDataBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun initDataBase() : AppDataBase?{
        if (appDataBase == null)
            appDataBase = AppDataBase(context = this)
        return appDataBase
    }

    fun replaceFragmentWithNoBackStack(targetFragment: Fragment, tagName: String) {
        val mFragmentManager = supportFragmentManager
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val currentFragment = mFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }
        fragmentTransaction
            .replace(R.id.fragment_container, targetFragment, tagName)
        fragmentTransaction.setPrimaryNavigationFragment(targetFragment)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitAllowingStateLoss()
    }

    fun changeFragmentWithoutReCreation(fragment: Fragment, tagFragmentName: String) {
        val mFragmentManager = supportFragmentManager
        val fragmentTransaction = mFragmentManager.beginTransaction()
        val currentFragment = mFragmentManager.primaryNavigationFragment
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment)
        }
        var fragmentTemp = mFragmentManager.findFragmentByTag(tagFragmentName)
        if (fragmentTemp == null) {
            fragmentTemp = fragment
            fragmentTransaction.add(R.id.fragment_container, fragmentTemp, tagFragmentName)
        } else {
            fragmentTransaction.show(fragmentTemp)
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentTemp)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
    }

}