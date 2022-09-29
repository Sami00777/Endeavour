package com.xanroid.endeavour

import android.app.Activity
import android.content.Context
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.room.Room
import com.xanroid.endeavour.main_list_data.ProductDao
import com.xanroid.endeavour.main_list_data.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    fun provideNavController(activity: Activity): NavController {
        return activity.findNavController(R.id.fragmentContainerView)
    }

    @Singleton
    @Provides
    fun provideDao(database: ProductDatabase): ProductDao
            = database.productDao()

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context)
            = Room.databaseBuilder(
        context,ProductDatabase::class.java,"product_database"
    ).fallbackToDestructiveMigration().build()

}