package com.harrikirik.rescheck

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.harrikirik.rescheck.util.isDebugBuild
import timber.log.Timber

class App : Application()  {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

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
