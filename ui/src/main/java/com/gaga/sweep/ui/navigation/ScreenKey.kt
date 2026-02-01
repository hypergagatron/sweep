package com.gaga.sweep.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface ScreenKey : NavKey {
    @Serializable
    data object Start : ScreenKey, NavKey

    @Serializable
    data object SearchResults : ScreenKey, NavKey

    @Serializable
    data class VenueDetails(val id: String) : ScreenKey, NavKey
}
