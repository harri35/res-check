package com.harrikirik.rescheck.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.harrikirik.mvp.MvpFragment
import com.harrikirik.rescheck.R

class MainFragment : MvpFragment<MainPresenter, MainState>(), MainContract.View {
    private lateinit var input: EditText
    private lateinit var buttonReverse: Button

    override fun createPresenter(): MainPresenter {
        val model = MainModel()
        presenter = MainPresenter(this, model)
        model.presenter = presenter
        return presenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.main_fragment_layout, container, false)
        input = view.findViewById(R.id.input)
        buttonReverse = view.findViewById(R.id.button_reverse)
        buttonReverse.setOnClickListener { presenter.onReverseClicked() }
        return view
    }

    override fun setInputText(text: String) {
        input.setText(text)
        input.setSelection(input.text.length)
    }

    override fun setDefaultInputText() {
        setInputText(getString(R.string.text_default_input))
    }

    override fun getInputText(): String {
        return input.text.toString()
    }

    override fun showError(error: Throwable) {
        Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
