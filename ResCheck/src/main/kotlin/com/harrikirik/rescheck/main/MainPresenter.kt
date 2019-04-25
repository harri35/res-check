package com.harrikirik.rescheck.main

import com.harrikirik.mvp.MvpPresenter

class MainPresenter(view: MainContract.View, model: MainContract.Model) :
        MvpPresenter<MainContract.View, MainContract.Model, MainState>(view, model),
        MainContract.ModelPresenter,
        MainContract.ViewPresenter {

    override fun subscribe(state: MainState?) {
        super.subscribe(state)
        if (state != null) {
            view.setInputText(state.text)
        } else {
            view.setDefaultInputText()
        }
    }

    override fun getState(): MainState? {
        return MainState(view.getInputText())
    }

    override fun onReverseClicked() {
        model.reverseText(view.getInputText())
    }

    override fun onReverseError(error: Throwable) {
        view.showError(error)
    }

    override fun onReverseSuccess(text: String) {
        view.setInputText(text)
    }
}
