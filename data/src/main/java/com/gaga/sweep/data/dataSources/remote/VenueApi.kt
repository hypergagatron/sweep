package com.gaga.sweep.data.dataSources.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface VenueApi {
    @GET("places/search")
    suspend fun searchPlaces(
        @Query("ll") latLon: String,
        @Query("query") query: String?,
        @Query("radius") radius: Int?,
        @Query("limit") limit: Int = 20): Response<VenuesDto>
}
