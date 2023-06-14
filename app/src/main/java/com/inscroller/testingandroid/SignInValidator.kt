package com.inscroller.testingandroid

object SignInValidator {

    fun validateSignIn(email: String, password: String): Boolean {
        return if (email.isBlank() || password.isBlank()) {
            false
        } else password.length >= 6
    }
}