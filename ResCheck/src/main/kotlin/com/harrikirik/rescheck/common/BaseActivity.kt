package com.harrikirik.rescheck.common

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.harrikirik.rescheck.R

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity() {

    protected fun addFragment(tag: String, newInstance: androidx.fragment.app.Fragment) {
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
