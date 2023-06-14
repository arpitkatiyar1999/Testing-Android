package com.inscroller.testingandroid

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class SignInValidatorTest {
    @Test
    fun `empty email return false`() {
        val result = SignInValidator.validateSignIn("", "12356")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty password return false`() {
        val result = SignInValidator.validateSignIn("abc@gmail.com", "")
        assertThat(result).isFalse()
    }

    @Test
    fun `password less than siz characters return false`() {
        val result = SignInValidator.validateSignIn("abc@gmail.com", "12345")
        assertThat(result).isFalse()
    }

    @Test
    fun `valid email password return true`() {
        val result = SignInValidator.validateSignIn("abc@gmail.com", "123456")
        assertThat(result).isTrue()
    }
}