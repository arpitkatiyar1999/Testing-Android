package com.inscroller.testingandroid.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem):Long

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem):Int

    @Query("SELECT * FROM ShoppingItem")
    fun observeAllShoppingItem(): LiveData<List<ShoppingItem>>?

    @Query("SELECT SUM(price*amount) FROM ShoppingItem")
    fun observeTotalPrice(): LiveData<Float>?
}