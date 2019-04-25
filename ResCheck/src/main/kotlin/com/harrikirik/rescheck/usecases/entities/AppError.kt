package com.harrikirik.rescheck.usecases.entities

class AppError(val code: Long, message: String?) : Error(message) {

    override fun toString(): String {
        return "$code $message"
    }

    companion object {
        const val ERROR_CODE_LOCAL_INVALID_REVERSE_INPUT = -100L
    }
}
