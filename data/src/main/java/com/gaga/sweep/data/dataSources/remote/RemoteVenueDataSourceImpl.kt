package com.gaga.sweep.data.dataSources.remote

import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.dataSources.RemoteDataSource
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.models.VenueSearchParams
import javax.inject.Inject

class RemoteVenueDataSourceImpl @Inject constructor(
    private val venueApi: VenueApi
) : RemoteDataSource<VenueSearchParams, Venue> {

    override suspend fun getItems(params: VenueSearchParams): DataStatus<List<Venue>> {
        val ll = "${params.lat},${params.lon}"
        val response = venueApi.searchPlaces(ll, params.query, params.radiusMeters)
        if (response.isSuccessful) {
            return DataStatus.Success(response.body()?.results?.map { it.toDomain() })
        } else {
            val errorMessage = response.errorBody()?.string() ?: "Unknown error"
            return DataStatus.Failure(Throwable(errorMessage))
        }
    }
}
