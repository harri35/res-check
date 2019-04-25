package com.harrikirik.rescheck.main

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.harrikirik.rescheck.usecases.entities.AppError
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class MainPresenterTest {
    private lateinit var model: MainContract.Model
    private lateinit var view: MainContract.View
    private lateinit var presenter: MainPresenter

    @Before
    fun setup() {
        model = mock()
        view = mock()
        presenter = MainPresenter(view, model)
    }

    @Test
    fun subscribeWithDefaultValue() {
        presenter.subscribe(null)
        verify(view).setDefaultInputText()
    }

    @Test
    fun subscribeWithState() {
        val text = "hello, hei!"
        val state = MainState(text)
        presenter.subscribe(state)
        verify(view).setInputText(eq(text))
    }

    @Test
    fun getState() {
        val text = "hello, hei!"
        whenever(view.getInputText()).thenReturn(text)
        val returnedState = presenter.getState()
        assertNotNull(returnedState)
        assertEquals(text, returnedState.text)
    }

    @Test
    fun onReverseClicked() {
        val text = "hello, hei!"
        whenever(view.getInputText()).thenReturn(text)
        presenter.onReverseClicked()
        verify(view).getInputText()
        verify(model).reverseText(eq(text))
    }

    @Test
    fun onReverseError() {
        val error = AppError(AppError.ERROR_CODE_LOCAL_INVALID_REVERSE_INPUT, "error")
        presenter.onReverseError(error)
        verify(view).showError(eq(error))
    }

    @Test
    fun onReverseSuccess() {
        val text = "hello, hei!"
        presenter.onReverseSuccess(text)
        verify(view).setInputText(eq(text))
    }
}
