package com.harrikirik.rescheck.main

import io.reactivex.disposables.Disposable
import com.harrikirik.mvp.MvpModel
import com.harrikirik.rescheck.common.util.dispose
import com.harrikirik.rescheck.common.util.singleSchedulers
import com.harrikirik.rescheck.usecases.ReverseUseCase

class MainModel : MvpModel<MainPresenter>(), MainContract.Model {
    private var disposable: Disposable? = null

    override fun reverseText(text: String) {
        disposable = ReverseUseCase().execute(text)
                .compose(singleSchedulers())
                .subscribe({ reversed -> presenter.onReverseSuccess(reversed) }, { error -> presenter.onReverseError(error) })
    }

    override fun onDestroy() {
        super.onDestroy()
        dispose(disposable)
    }
}
