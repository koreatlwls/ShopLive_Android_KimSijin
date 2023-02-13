package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.database.ShopLiveApplicationDataBase
import com.example.data.local.database.ShopLiveApplicationDataBase.Companion.DB_NAME
import com.example.data.local.database.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DataBaseModule {

    @Provides
    @Singleton
    fun provideApplicationDataBase(@ApplicationContext context: Context): ShopLiveApplicationDataBase {
        return Room.databaseBuilder(
            context,
            ShopLiveApplicationDataBase::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoriteDao(
        shopLiveApplicationDataBase: ShopLiveApplicationDataBase
    ): FavoriteDao {
        return shopLiveApplicationDataBase.getFavoriteDao()
    }

}