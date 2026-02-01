package com.gaga.sweep.domain.repositories

import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue
import kotlinx.coroutines.flow.Flow

interface VenueRepository {

    suspend fun getMatchingVenuesInRadius(query: String, radiusMeters: Int = 2000): Flow<DataStatus<List<Venue>>>

    suspend fun getVenueDetails(id: String): Flow<DataStatus<Venue>>
}
