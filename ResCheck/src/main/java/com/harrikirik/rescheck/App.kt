package com.harrikirik.rescheck

import android.app.Application
import com.harrikirik.rescheck.util.isDebugBuild
import timber.log.Timber

class App : Application()  {

    override fun onCreate() {
        super.onCreate()
        initLogging()
    }

    private fun initLogging() {
        if (isDebugBuild()) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
