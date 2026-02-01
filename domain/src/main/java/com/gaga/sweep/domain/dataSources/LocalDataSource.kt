package com.gaga.sweep.domain.dataSources

import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.models.VenueSearchParams
import kotlinx.coroutines.flow.Flow

interface LocalDataSource<P, T> {

    suspend fun saveItems(venues: List<T>)

    fun getMatchingItems(
        params: P
    ): Flow<DataStatus<List<T>>>

    suspend fun getItem(id: String): Flow<DataStatus<T>>
}
