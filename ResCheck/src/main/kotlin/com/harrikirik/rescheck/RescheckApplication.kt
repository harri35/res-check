package com.harrikirik.rescheck

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree

class RescheckApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
