package com.inscroller.testingandroid.repo

import androidx.lifecycle.LiveData
import com.inscroller.testingandroid.data.local.ShoppingDatabase
import com.inscroller.testingandroid.data.local.ShoppingItem
import com.inscroller.testingandroid.data.remote.ApiInterface
import com.inscroller.testingandroid.data.remote.responses.ImageResponse
import com.inscroller.testingandroid.utils.Resource
import javax.inject.Inject

class ShoppingRepoImpl @Inject constructor(private val shoppingDatabase: ShoppingDatabase, private val apiInterface: ApiInterface) : ShoppingRepo {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem): Long {
        return shoppingDatabase.shoppingDao().insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem): Int {
        return shoppingDatabase.shoppingDao().deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>? {
        return shoppingDatabase.shoppingDao().observeAllShoppingItem()
    }

    override fun observeTotalPrice(): LiveData<Float>? {
        return shoppingDatabase.shoppingDao().observeTotalPrice()
    }

    override suspend fun searchForImage(query: String): Resource<ImageResponse> {
        return try {
            val response = apiInterface.getImages(query)
            if (response.isSuccessful) {
                response.body()?.let {
                    Resource.success(it)
                } ?: Resource.error("Some Error Occurred", null)
            } else {
                Resource.error("Some Error Occurred", null)
            }
        } catch (exception: Exception) {
            Resource.error(exception.localizedMessage ?: "Some error occurred", null)
        }
    }
}