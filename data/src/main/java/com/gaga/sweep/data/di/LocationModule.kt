package com.gaga.sweep.data.di

import android.content.Context
import com.gaga.sweep.data.dataSources.local.location.LocationDataSourceImpl
import com.gaga.sweep.domain.dataSources.LocationDataSource
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Provides
    @Singleton
    fun provideLocationDataSource(
        fusedLocationProviderClient: FusedLocationProviderClient
    ): LocationDataSource {
        return LocationDataSourceImpl(fusedLocationProviderClient)
    }
}