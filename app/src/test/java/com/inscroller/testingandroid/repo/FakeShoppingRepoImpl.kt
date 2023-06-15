package com.inscroller.testingandroid.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.inscroller.testingandroid.data.local.ShoppingItem
import com.inscroller.testingandroid.data.remote.responses.ImageResponse
import com.inscroller.testingandroid.utils.Resource

class FakeShoppingRepoImpl : ShoppingRepo {
    private val shoppingItemList = mutableListOf<ShoppingItem>()
    private val observeAllShoppingItem = MutableLiveData<List<ShoppingItem>>(shoppingItemList)
    private val observeTotalPrice = MutableLiveData<Float>()
    private val shouldReturnNetworkError = false

    private fun refreshObserveAllShoppingList() {
        observeAllShoppingItem.postValue(shoppingItemList)
    }

    private fun refreshObserveTotalPrice() {
        var totalPrice = 0f
        shoppingItemList.forEach {
            totalPrice += it.price * it.amount
        }
        observeTotalPrice.postValue(totalPrice)
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem): Long {
        return if (shoppingItemList.add(shoppingItem)) {
            refreshObserveAllShoppingList()
            refreshObserveTotalPrice()
            shoppingItemList.lastIndex.toLong()
        } else {
            -1
        }
    }


    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem): Int {
        return if (shoppingItemList.remove(shoppingItem)) {
            refreshObserveAllShoppingList()
            refreshObserveTotalPrice()
            1
        } else {
            0
        }
    }

    override fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>? {
        return observeAllShoppingItem
    }

    override fun observeTotalPrice(): LiveData<Float>? {
        return observeTotalPrice
    }

    override suspend fun searchForImage(query: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError) {
            Resource.error("Some Error Occurred", null)
        } else {
            Resource.success(ImageResponse(0, 0, arrayListOf()))
        }
    }

}