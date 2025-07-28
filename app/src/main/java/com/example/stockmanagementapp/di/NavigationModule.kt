package com.example.stockmanagementapp.di

import com.example.stockmanagementapp.view.navigator.ComposeNavigator
import com.example.stockmanagementapp.view.navigator.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = ComposeNavigator()
}