package com.gaga.sweep.ui.screens.venueDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.repositories.VenueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VenueDetailsViewModel @Inject constructor(
    private val venueRepository: VenueRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<DataStatus<Venue>>(DataStatus.Loading)
    val uiState: StateFlow<DataStatus<Venue>> = _uiState.asStateFlow()

    fun getVenue(id: String) {
        viewModelScope.launch {
            venueRepository.getVenueDetails(id)
                .onStart { _uiState.value = DataStatus.Loading }
                .catch { e -> _uiState.value = DataStatus.Failure(Throwable(e.message ?: "Error")) }
                .collect { venue ->
                    _uiState.value = venue
                }
        }
    }
}
