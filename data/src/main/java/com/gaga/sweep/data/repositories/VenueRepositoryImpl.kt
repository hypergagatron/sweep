package com.gaga.sweep.data.repositories

import com.gaga.sweep.data.DataOrchestrator
import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.dataSources.LocalDataSource
import com.gaga.sweep.domain.dataSources.LocationDataSource
import com.gaga.sweep.domain.dataSources.RemoteDataSource
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.models.VenueSearchParams
import com.gaga.sweep.domain.repositories.VenueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VenueRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val localDataSource: LocalDataSource<VenueSearchParams, Venue>,
    private val remoteDataSource: RemoteDataSource<VenueSearchParams, Venue>
) : VenueRepository {

    override suspend fun getMatchingVenuesInRadius(
        query: String, radiusMeters: Int
    ): Flow<DataStatus<List<Venue>>> = flow {

        emit(DataStatus.Loading)

        val locationStatus = locationDataSource.getLocation()

        if (locationStatus is DataStatus.Success) {
            locationStatus.data?.let {

                val venueParams = VenueSearchParams(
                    query,
                    it.latitude,
                    it.longitude,
                    radiusMeters
                )

                val dataOrchestrator = DataOrchestrator(localDataSource, remoteDataSource)

                dataOrchestrator.getItems(venueParams).collect { result -> emit(result) }
            }
        } else {
            emit(DataStatus.Failure(Throwable("Location not available, cannot retrieve data")))
        }
    }


    override suspend fun getVenueDetails(id: String): Flow<DataStatus<Venue>> =
        localDataSource.getItem(id)

}
