package com.harrikirik.mvp

import com.harrikirik.mvp.contracts.MvpModelContract
import com.harrikirik.mvp.contracts.MvpModelPresenterContract

/**
 * Mvp base Model implementation
 */
abstract class MvpModel<PRESENTER : MvpModelPresenterContract> : MvpModelContract {

    lateinit var presenter: PRESENTER

    override fun onDestroy() {
        // No cleanup here
    }
}
