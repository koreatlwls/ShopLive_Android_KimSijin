package com.example.data.di

import com.example.data.local.source.FavoriteLocalDataSource
import com.example.data.local.source.FavoriteLocalDataSourceImpl
import com.example.data.remote.source.MarvelRemoteDataSource
import com.example.data.remote.source.MarvelRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Binds
    @Singleton
    fun provideMarvelRemoteDataSource(
        marvelRemoteDataSourceImpl: MarvelRemoteDataSourceImpl
    ): MarvelRemoteDataSource

    @Binds
    @Singleton
    fun provideFavoriteLocalDataSource(
        favoriteLocalDataSourceImpl: FavoriteLocalDataSourceImpl
    ): FavoriteLocalDataSource

}