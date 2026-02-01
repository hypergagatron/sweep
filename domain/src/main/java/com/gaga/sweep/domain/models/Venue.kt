package com.gaga.sweep.domain.models

data class Venue(
    val id: String,
    val name: String?,
    val lon: Double?,
    val lat: Double?,

    val imageUrls: List<String>? = null,
    val address: String? = null,
    val hours: String? = null,
    val contact: String? = null
)
