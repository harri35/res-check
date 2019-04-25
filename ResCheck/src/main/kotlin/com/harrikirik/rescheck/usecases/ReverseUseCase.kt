package com.harrikirik.rescheck.usecases

import io.reactivex.Single
import com.harrikirik.rescheck.usecases.entities.AppError

class ReverseUseCase {

    fun execute(text: String): Single<String> {
        return Single.fromCallable { reverse(text) }
    }

    private fun reverse(text: String): String {
        val reversed = text.reversed()
        if (text == reversed) {
            throw AppError(AppError.ERROR_CODE_LOCAL_INVALID_REVERSE_INPUT, "String $text can't be reversed.")
        }
        return reversed
    }
}
