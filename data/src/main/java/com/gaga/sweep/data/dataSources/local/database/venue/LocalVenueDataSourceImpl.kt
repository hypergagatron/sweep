package com.gaga.sweep.data.dataSources.local.database.venue

import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.dataSources.LocalDataSource
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.models.VenueSearchParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class LocalVenueDataSourceImpl @Inject constructor(
    private val venueDao: VenueDao
) : LocalDataSource<VenueSearchParams, Venue> {

    override suspend fun saveItems(venues: List<Venue>) {
        venueDao.upsertVenues(venues.map { it.toEntity() })
    }

    override fun getMatchingItems(params: VenueSearchParams): Flow<DataStatus<List<Venue>>> =
        venueDao.getVenues(params.query)
            .map { venueEntities ->
                DataStatus.Success(
                    venueEntities
                        .map { it.toDomain() }
                        .filter { venue ->
                            venue.isInRadius(
                                userLat = params.lat,
                                userLon = params.lon,
                                radiusMeters = params.radiusMeters
                            )
                        }
                ) as DataStatus<List<Venue>>
            }.catch { e ->
                emit(DataStatus.Failure(e))
            }

    override suspend fun getItem(id: String): Flow<DataStatus<Venue>> {
        return venueDao.getVenueById(id).map { DataStatus.Success(it?.toDomain()) }
    }
}

private fun Venue.isInRadius(
    userLat: Double,
    userLon: Double,
    radiusMeters: Int
): Boolean {
    val rEarth = 6371000.0
    if (lat != null && lon != null) {
        val dLat = Math.toRadians(lat!! - userLat)
        val dLon = Math.toRadians(lon!! - userLon)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(userLat)) * cos(Math.toRadians(lat!!)) *
                sin(dLon / 2).pow(2)

        val distance = 2 * rEarth * asin(sqrt(a))
        return distance <= radiusMeters
    } else return false
}
