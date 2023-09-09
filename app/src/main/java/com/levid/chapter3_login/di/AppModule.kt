package com.levid.chapter3_login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import com.levid.chapter3_login.util.SampleLoginDispatchers

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSlimeDispatchers(): SampleLoginDispatchers {
        return SampleLoginDispatchers(
            default = Dispatchers.Default,
            main = Dispatchers.Main,
            io = Dispatchers.IO
        )
    }
}