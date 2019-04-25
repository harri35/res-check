package com.harrikirik.rescheck.main

import android.os.Bundle
import com.harrikirik.rescheck.R
import com.harrikirik.rescheck.common.BaseActivity

class MainActivity : BaseActivity() {
    companion object {
        const val TAG_MAIN_FRAGMENT = "TAG_MAIN_FRAGMENT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_layout)
        addFragment(TAG_MAIN_FRAGMENT, MainFragment.newInstance())
    }
}
