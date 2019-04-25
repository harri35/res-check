package com.harrikirik.mvp.contracts

/**
 * Presenter -> Controller communication interface
 */
interface MvpControllerContract<STATE : Any> {

    // Lifecycle
    fun subscribe(state: STATE?)
    fun unsubscribe()
    fun onDestroy()

    // Override this to provide state
    fun getState(): STATE? {
        return null
    }
}
