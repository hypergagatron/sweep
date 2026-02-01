package com.gaga.sweep.data.di

import android.content.Context
import androidx.room.Room
import com.gaga.sweep.data.dataSources.local.database.SweepDatabase
import com.gaga.sweep.data.dataSources.local.database.venue.LocalVenueDataSourceImpl
import com.gaga.sweep.data.dataSources.local.database.venue.VenueDao
import com.gaga.sweep.domain.dataSources.LocalDataSource
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.models.VenueSearchParams
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SweepDatabase {
        return Room.databaseBuilder(
            context,
            SweepDatabase::class.java,
            "venues_db"
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    fun provideVenueDao(database: SweepDatabase): VenueDao {
        return database.venueDao()
    }

    @Provides
    fun provideLocalVenueDataSource(venueDao: VenueDao): LocalDataSource<VenueSearchParams, Venue> {
        return LocalVenueDataSourceImpl(venueDao)
    }
}