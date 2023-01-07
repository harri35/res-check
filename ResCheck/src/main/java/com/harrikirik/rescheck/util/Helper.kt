package com.harrikirik.rescheck.util

import com.harrikirik.rescheck.BuildConfig

fun isDebugBuild(): Boolean {
    return BuildConfig.DEBUG
}
