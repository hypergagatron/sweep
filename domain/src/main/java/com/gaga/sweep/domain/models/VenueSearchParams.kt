package com.gaga.sweep.domain.models

data class VenueSearchParams(
    val query: String,
    val lat: Double,
    val lon: Double,
    val radiusMeters: Int
)
