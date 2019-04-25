package com.harrikirik.mvp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.harrikirik.mvp.contracts.MvpControllerContract
import com.harrikirik.mvp.contracts.MvpModelPresenterContract
import com.harrikirik.mvp.contracts.MvpViewContract
import com.harrikirik.mvp.contracts.MvpViewPresenterContract

/**
 * Base Activity for implementing an MVP view within one activity.
 * The activity acts as a controller (creating the MVP components) and as the View implementation, too.
 *
 * PRESENTER - Presenter implementation class
 * STATE - Optional State object class. Used in the presenter. If not needed then use 'Any'
 */
abstract class MvpActivity<PRESENTER, STATE> : AppCompatActivity(), MvpViewContract
        where STATE : Any,
              PRESENTER : MvpControllerContract<STATE>,
              PRESENTER : MvpViewPresenterContract,
              PRESENTER : MvpModelPresenterContract {

    protected abstract fun createPresenter(): PRESENTER

    protected lateinit var presenter: PRESENTER
    private lateinit var controller: MvpController<PRESENTER, STATE>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        val initialState = if (savedInstanceState == null) createInitialState() else null
        controller = MvpController(presenter, MvpStateBundler(createStateKey()), initialState)
        if (savedInstanceState != null) {
            controller.restoreState(savedInstanceState)
        }
        if (SubscribeLifecycleState.CREATED == getSubscribeLifecycleState()) {
            controller.subscribe()
        }
    }

    override fun onStart() {
        super.onStart()
        if (SubscribeLifecycleState.STARTED == getSubscribeLifecycleState()) {
            controller.subscribe()
        }
    }

    override fun onStop() {
        super.onStop()
        if (SubscribeLifecycleState.STARTED == getSubscribeLifecycleState()) {
            controller.unsubscribe()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        controller.saveState(outState)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (SubscribeLifecycleState.CREATED == getSubscribeLifecycleState()) {
            controller.unsubscribe()
        }
        controller.destroy()
    }

    open fun createInitialState(): STATE? {
        return null
    }

    protected open fun getSubscribeLifecycleState(): SubscribeLifecycleState {
        return SubscribeLifecycleState.CREATED
    }

    private fun createStateKey(): String {
        return "${this::class.java.name}.STATE"
    }
}
