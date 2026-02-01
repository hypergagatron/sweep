package com.gaga.sweep.ui.screens.search

import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue

data class VenueSearchResultsEvents(
    val onLocationPermissionGiven: () -> Unit,
    val onLocationPermissionRejected: () -> Unit,
    val onSearchTermChanged: (String) -> Unit,
    val onVenueClick: (String) -> Unit
)

data class VenueSearchResultsUiState(
    val results: DataStatus<List<Venue>>
)
