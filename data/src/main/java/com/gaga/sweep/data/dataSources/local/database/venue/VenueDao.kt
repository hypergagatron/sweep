package com.gaga.sweep.data.dataSources.local.database.venue

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface VenueDao {

    @Query("SELECT * FROM venues WHERE name LIKE '%' || :query || '%'")
    fun getVenues(query: String): Flow<List<VenueEntity>>

    @Query("SELECT * FROM venues WHERE id = :id")
    fun getVenueById(id: String): Flow<VenueEntity?>

    @Upsert
    suspend fun upsertVenues(venues: List<VenueEntity>)
}
