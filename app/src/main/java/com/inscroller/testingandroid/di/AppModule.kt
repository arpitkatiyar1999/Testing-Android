package com.inscroller.testingandroid.di

import android.content.Context
import androidx.room.Room
import com.inscroller.testingandroid.data.local.ShoppingDatabase
import com.inscroller.testingandroid.data.remote.ApiInterface
import com.inscroller.testingandroid.repo.ShoppingRepo
import com.inscroller.testingandroid.repo.ShoppingRepoImpl
import com.inscroller.testingandroid.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Provides
    @Singleton
    fun provideShoppingRepo(shoppingRepoImpl: ShoppingRepoImpl): ShoppingRepo = shoppingRepoImpl

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Singleton
    @Provides
    fun provideShoppingDatabase(context: Context): ShoppingDatabase = Room
        .databaseBuilder(context, ShoppingDatabase::class.java, Constants.DATABASE_NAME)
        .build()

    @Singleton
    @Provides
    fun providePixabayApi(): ApiInterface = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.PIXABAY_BASE_URL)
        .build()
        .create(ApiInterface::class.java)
}