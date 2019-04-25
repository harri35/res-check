package com.harrikirik.rescheck.usecases

import com.harrikirik.rescheck.usecases.entities.AppError
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class ReverseUseCaseTest {

    @Test
    fun executeWithValidInput_1() {
        assertEquals("ba", ReverseUseCase().execute("ab").blockingGet())
    }

    @Test
    fun executeWithValidInput_2() {
        assertEquals("hello!", ReverseUseCase().execute("!olleh").blockingGet())
    }

    @Test
    fun executeWithValidInput_3() {
        assertEquals(":)", ReverseUseCase().execute("):").blockingGet())
    }

    @Test(expected = AppError::class)
    fun executeWithInvalidInput_1() {
        ReverseUseCase().execute("a").blockingGet()
    }

    @Test(expected = AppError::class)
    fun executeWithInvalidInput_2() {
        ReverseUseCase().execute("aa").blockingGet()
    }

    @Test(expected = AppError::class)
    fun executeWithInvalidInput_3() {
        ReverseUseCase().execute("aba").blockingGet()
    }

    @Test(expected = AppError::class)
    fun executeWithInvalidInput_4() {
        ReverseUseCase().execute("").blockingGet()
    }

    @Test
    fun executeWithInvalidInput_5() {
        try {
            ReverseUseCase().execute("...").blockingGet()
            fail("Should throw an Error!")
        } catch (e: Error) {
            assertTrue { e is AppError }
            assertEquals(AppError.ERROR_CODE_LOCAL_INVALID_REVERSE_INPUT, (e as AppError).code)
        }
    }
}
