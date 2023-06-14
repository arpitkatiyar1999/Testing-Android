package com.inscroller.testingandroid

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ChecksEqualityTest {
    private lateinit var checksEquality: ChecksEquality

    @Before
    fun setUp() {
        checksEquality = ChecksEquality()
    }

    @Test
    fun equalString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = checksEquality.isEqual(context, R.string.hello, "hello")
        assertThat(result).isTrue()
    }

    @Test
    fun unequalString_returnsFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = checksEquality.isEqual(context, R.string.hello, "hello friend")
        assertThat(result).isFalse()
    }

}