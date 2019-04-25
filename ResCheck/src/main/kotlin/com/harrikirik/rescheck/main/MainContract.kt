package com.harrikirik.rescheck.main

import com.harrikirik.mvp.contracts.MvpModelContract
import com.harrikirik.mvp.contracts.MvpModelPresenterContract
import com.harrikirik.mvp.contracts.MvpViewContract
import com.harrikirik.mvp.contracts.MvpViewPresenterContract

class MainContract {
    interface View : MvpViewContract {
        fun getInputText(): String
        fun setInputText(text: String)
        fun showError(error: Throwable)
        fun setDefaultInputText()
    }

    interface ViewPresenter : MvpViewPresenterContract {
        fun onReverseClicked()
    }

    interface ModelPresenter : MvpModelPresenterContract {
        fun onReverseSuccess(text: String)
        fun onReverseError(error: Throwable)
    }

    interface Model : MvpModelContract {
        fun reverseText(text: String)
    }
}
