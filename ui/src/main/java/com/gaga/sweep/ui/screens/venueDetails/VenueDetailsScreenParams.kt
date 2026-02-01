package com.gaga.sweep.ui.screens.venueDetails

import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue

data class VenueDetailsUiState(
    val venueStatus: DataStatus<Venue>
)

data class VenueDetailsEvents(
    val onBack: () -> Unit
)
