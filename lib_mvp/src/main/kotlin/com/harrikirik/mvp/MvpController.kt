package com.harrikirik.mvp

import android.os.Bundle
import com.harrikirik.mvp.contracts.MvpControllerContract

/**
 * Internal controller module to handle presenter lifecycle events and state object handling
 */
internal class MvpController<PRESENTER : MvpControllerContract<STATE>, STATE : Any>(
    protected val presenter: PRESENTER,
    protected val bundler: StateBundler<STATE>,
    protected var initialState: STATE? = null
) {

    private var stateBundle: Bundle? = null

    fun restoreState(savedInstanceState: Bundle) {
        stateBundle = savedInstanceState.getBundle(getStateKey())
    }

    fun saveState(outState: Bundle) {
        savePresenterState()
        outState.putBundle(getStateKey(), stateBundle)
    }

    // Lifecycle
    fun subscribe() {
        presenter.subscribe(loadPresenterState())
    }

    fun unsubscribe() {
        presenter.unsubscribe()
    }

    fun destroy() {
        presenter.onDestroy()
    }

    private fun getStateKey(): String {
        return "${presenter::class.java.name}.MVP_CONTROLLER_STATE"
    }

    private fun loadPresenterState(): STATE? {
        val bundle = stateBundle
        return if (bundle == null) {
            initialState
        } else {
            bundler.read(bundle)
        }
    }

    private fun savePresenterState() {
        val state = presenter.getState()
        if (state != null) {
            val bundle = stateBundle ?: Bundle()
            bundler.write(bundle, state)
            stateBundle = bundle
        }
    }
}
