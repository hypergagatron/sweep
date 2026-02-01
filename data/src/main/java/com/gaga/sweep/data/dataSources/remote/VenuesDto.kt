package com.gaga.sweep.data.dataSources.remote

import com.gaga.sweep.domain.models.Venue
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VenuesDto(
    val results: List<VenueDto>
)

@Serializable
data class VenueDto(
    @SerializedName("fsq_place_id")
    val fsqId: String,
    val name: String? = "",
    val description: String? = "",
    val latitude: Double?,
    val longitude: Double?,

    val location: LocationDto?,
    val hours: HoursDto? = null,
    val photos: List<PhotoDto>? = emptyList(),
    val tel: String? = null,
    val email: String? = null,
    val website: String? = null
)

@Serializable
data class LocationDto(
    @SerializedName("formatted_address")
    val formattedAddress: String?
)

@Serializable
data class HoursDto(
    val display: String? = null,
    @SerialName("open_now") val openNow: Boolean? = null
)

@Serializable
data class PhotoDto(
    val prefix: String?,
    val suffix: String?
)

fun VenueDto.toDomain() = Venue(
    id = fsqId,
    name = name,
    lon = longitude,
    lat = latitude,
    // hardcoding a few images since foursquare api returning images must be paid
    imageUrls = listOf(
        "https://loveincorporated.blob.core.windows.net/contentimages/gallery/6a985aaa-8a95-4382-97a9-91cdf96f43d3-Moraine_Lake_Dennis_Frates_Alamy_Stock_Photo.jpg",
        "https://images.pexels.com/photos/29794379/pexels-photo-29794379.jpeg?_gl=1*wp85w9*_ga*MTA2NDc1MzEzMC4xNzY5OTcwMjYw*_ga_8JE65Q40S6*czE3Njk5NzAyNjAkbzEkZzEkdDE3Njk5NzAyNjckajUzJGwwJGgw",
        "https://images.pexels.com/photos/35664914/pexels-photo-35664914.jpeg?_gl=1*u5qz5j*_ga*MTA2NDc1MzEzMC4xNzY5OTcwMjYw*_ga_8JE65Q40S6*czE3Njk5NzAyNjAkbzEkZzEkdDE3Njk5NzAyOTkkajIxJGwwJGgw"
    ),
    address = location?.formattedAddress,
    hours = hours?.display,
    tel
)
