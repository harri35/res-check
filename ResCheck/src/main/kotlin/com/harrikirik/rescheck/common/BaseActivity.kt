package com.harrikirik.rescheck.common

import android.annotation.SuppressLint
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.harrikirik.rescheck.R

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected fun addFragment(tag: String, newInstance: Fragment) {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null) {
            return
        }
        supportFragmentManager
                .beginTransaction()
                .add(R.id.layout_fragment_container, newInstance, tag)
                .commitAllowingStateLoss()
    }
}
