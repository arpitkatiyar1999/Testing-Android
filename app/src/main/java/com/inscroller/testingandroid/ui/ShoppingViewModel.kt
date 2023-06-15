package com.inscroller.testingandroid.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.inscroller.testingandroid.data.local.ShoppingItem
import com.inscroller.testingandroid.data.remote.responses.ImageResponse
import com.inscroller.testingandroid.repo.ShoppingRepo
import com.inscroller.testingandroid.utils.Event
import com.inscroller.testingandroid.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(private val repo: ShoppingRepo) : ViewModel() {

    val shoppingListLiveData = repo.observeAllShoppingItem()

    val totalPriceLiveData = repo.observeTotalPrice()

    private val _imagesListResponseLiveData = MutableLiveData<Event<Resource<ImageResponse>>>()
    val imagesListResponseLiveData: LiveData<Event<Resource<ImageResponse>>> get() = _imagesListResponseLiveData

    private val _currentImageResponseLiveData = MutableLiveData<String>()
    val currentImageResponseLiveData: LiveData<String> get() = _currentImageResponseLiveData

    private val _deleteShoppingItemResponseLiveData = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val deleteShoppingItemResponseLiveData: LiveData<Event<Resource<ShoppingItem>>> get() = _deleteShoppingItemResponseLiveData

    private val _insertShoppingItemResponseLiveData = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemResponseLiveData: LiveData<Event<Resource<ShoppingItem>>> get() = _insertShoppingItemResponseLiveData

    fun setCurrentImageUrl(url: String) {
        _currentImageResponseLiveData.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        _deleteShoppingItemResponseLiveData.postValue(Event(Resource.loading(shoppingItem)))
        viewModelScope.launch {
            when (val result = repo.deleteShoppingItem(shoppingItem)) {
                0 -> {
                    _deleteShoppingItemResponseLiveData.postValue(
                        Event(Resource.error("some error occurred", shoppingItem))
                    )
                }

                else -> {
                    _deleteShoppingItemResponseLiveData.postValue(
                        Event(Resource.success(shoppingItem))
                    )
                }
            }
        }
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) {
        _insertShoppingItemResponseLiveData.postValue(Event(Resource.loading(shoppingItem)))
        viewModelScope.launch {
            when (val result = repo.insertShoppingItem(shoppingItem)) {
                -1L -> {
                    _insertShoppingItemResponseLiveData.postValue(
                        Event(Resource.error("Some Error occurred", shoppingItem))
                    )
                }

                else -> {
                    _insertShoppingItemResponseLiveData.postValue(
                        Event(Resource.success(shoppingItem))
                    )
                }
            }
        }
    }

    fun searchImage(query: String) {

    }
}