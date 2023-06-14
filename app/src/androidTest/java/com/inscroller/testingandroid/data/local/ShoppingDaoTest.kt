package com.inscroller.testingandroid.data.local

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.google.common.truth.Truth.assertWithMessage
import com.inscroller.testingandroid.getOrAwaitValue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room
            .inMemoryDatabaseBuilder(context, ShoppingDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = database.shoppingDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("Banana", 100, 1f, "")
        val result = dao.insertShoppingItem(shoppingItem)
        assertThat(result).isNotEqualTo(-1)
    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("Banana", 100, 1f, "")
        var result = dao.insertShoppingItem(shoppingItem)
        if (result != -1L) {
            shoppingItem.id = result.toInt()
            val res = dao.deleteShoppingItem(shoppingItem)
            assertThat(res).isGreaterThan(0)
        } else {
            assertWithMessage("Insertion failed").fail()
        }
    }

    @Test
    fun observeShoppingItems() = runTest {
        val shoppingItem1 = ShoppingItem("Banana", 100, 1f, "")
        val shoppingItem2 = ShoppingItem("Banana", 100, 1f, "")
        val shoppingItem3 = ShoppingItem("Banana", 100, 1f, "")
        val result1 = dao.insertShoppingItem(shoppingItem1)
        val result2 = dao.insertShoppingItem(shoppingItem2)
        val result3 = dao.insertShoppingItem(shoppingItem3)
        var count = 0
        if (result1 != -1L)
            count++
        if (result2 != -1L) {
            count++
        }
        if (result3 != -1L) {
            count++
        }
        if (count > 0) {
            val observeResult = dao.observeAllShoppingItem()?.getOrAwaitValue()
            assertThat(observeResult?.size).isEqualTo(count)
        } else {
            assertWithMessage("All Insertion failed").fail()
        }
    }

    @Test
    fun observeTotalPrice() = runTest {
        val shoppingItem1 = ShoppingItem("Banana", 10, 1f, "")
        val shoppingItem2 = ShoppingItem("Banana", 100, 2f, "")
        val shoppingItem3 = ShoppingItem("Banana", 1000, 3f, "")
        val result1 = dao.insertShoppingItem(shoppingItem1)
        val result2 = dao.insertShoppingItem(shoppingItem2)
        val result3 = dao.insertShoppingItem(shoppingItem3)
        var count = 0f
        if (result1 != -1L) {
            count += shoppingItem1.price * shoppingItem1.amount
        }
        if (result2 != -1L) {
            count += shoppingItem2.price * shoppingItem2.amount
        }
        if (result3 != -1L) {
            count += shoppingItem3.price * shoppingItem3.amount
        }
        if (count > 0) {
            val observePrice = dao.observeTotalPrice()?.getOrAwaitValue()
            assertThat(observePrice).isEqualTo(count)
        } else {
            assertWithMessage("All Insertion Failed").fail()
        }
    }
}