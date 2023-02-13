package com.example.data.di

import com.example.data.repository.FavoriteRepositoryImpl
import com.example.data.repository.MarvelRepositoryImpl
import com.example.domain.repository.FavoriteRepository
import com.example.domain.repository.MarvelRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    @Singleton
    fun provideMarvelRepository(
        marvelRepositoryImpl: MarvelRepositoryImpl
    ): MarvelRepository

    @Binds
    @Singleton
    fun provideFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository

}