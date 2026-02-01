package com.gaga.sweep.data.dataSources.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gaga.sweep.data.dataSources.local.database.venue.RoomConverters
import com.gaga.sweep.data.dataSources.local.database.venue.VenueDao
import com.gaga.sweep.data.dataSources.local.database.venue.VenueEntity

@Database(entities = [VenueEntity::class], version = 4, exportSchema = false)
@TypeConverters(RoomConverters::class)
abstract class SweepDatabase : RoomDatabase() {
    abstract fun venueDao(): VenueDao
}
