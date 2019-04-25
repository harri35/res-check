package com.harrikirik.mvp

import com.harrikirik.mvp.contracts.MvpControllerContract
import com.harrikirik.mvp.contracts.MvpModelContract
import com.harrikirik.mvp.contracts.MvpModelPresenterContract
import com.harrikirik.mvp.contracts.MvpViewContract
import com.harrikirik.mvp.contracts.MvpViewPresenterContract

/**
 * Mvp base Presenter implementation.
 * VIEW - View impl
 * MODEL - Model impl
 * STATE - Optional state class. Use 'Any' if not needed.
 *
 * An instance of STATE can be provided by overriding getState() function. The instance returned
 * by getState() (called before unsubscribe()) will be returned to the presenter in subscribe()
 *
 * Presenter is not saved anywhere and it has 2 lifecycle methods:
 * subscribe() - Everything is set up. Can start loading internal data if needed
 * unsubscribe() - Time to do any resource cleanup before the presenter is destroyed
 */
open class MvpPresenter<VIEW : MvpViewContract, MODEL : MvpModelContract, STATE : Any>(
    protected val view: VIEW,
    protected val model: MODEL
) : MvpModelPresenterContract, MvpViewPresenterContract, MvpControllerContract<STATE> {

    protected var subscribed: Boolean = false

    // Lifecycle
    override fun subscribe(state: STATE?) {
        subscribed = true
        // Do nothing
    }

    override fun unsubscribe() {
        subscribed = false
        // Do nothing
    }

    override fun onDestroy() {
        model.onDestroy()
    }

    // Override this to provide state
    override fun getState(): STATE? {
        return null
    }

    override fun onBackPressed() {
        view.finish()
    }
}
