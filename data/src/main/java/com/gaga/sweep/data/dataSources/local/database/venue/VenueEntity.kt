package com.gaga.sweep.data.dataSources.local.database.venue

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.gaga.sweep.domain.models.Venue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType)
    }
}

@Entity(tableName = "venues")
data class VenueEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(collate = ColumnInfo.NOCASE)
    val name: String?,
    val lon: Double?,
    val lat: Double?,
    val imageUrls: List<String>?,
    val address: String?,
    val contact: String?,
    val website: String?
)

fun VenueEntity.toDomain() = Venue(id, name, lon, lat, imageUrls, address, contact, website)

fun Venue.toEntity() = VenueEntity(id, name, lon, lat, imageUrls, address, contact, website)
