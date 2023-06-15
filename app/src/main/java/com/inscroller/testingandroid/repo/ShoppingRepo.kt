package com.inscroller.testingandroid.repo

import androidx.lifecycle.LiveData
import com.inscroller.testingandroid.data.local.ShoppingItem
import com.inscroller.testingandroid.data.remote.responses.ImageResponse
import com.inscroller.testingandroid.utils.Resource

interface ShoppingRepo {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem): Long

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem): Int

    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>?

    fun observeTotalPrice(): LiveData<Float>?

    suspend fun searchForImage(query: String): Resource<ImageResponse>
}