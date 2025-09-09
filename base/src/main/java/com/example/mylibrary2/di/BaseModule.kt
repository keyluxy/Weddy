package com.example.mylibrary2.di

import com.example.mylibrary2.db.DatabaseService
import com.example.mylibrary2.network.NetworkService
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class BaseModule {

    @Provides
    @Singleton
    fun provideDatabaseService() = DatabaseService()

    @Provides
    @Singleton
    fun provideNetworkService() = NetworkService()

}

@Singleton
@Component(modules = [BaseModule::class])
interface BaseComponent {
    fun databaseService(): DatabaseService
    fun networkService(): NetworkService
}