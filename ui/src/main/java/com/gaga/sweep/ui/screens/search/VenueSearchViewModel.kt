package com.gaga.sweep.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.repositories.VenueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VenueSearchViewModel @Inject constructor(
    private val venuesRepository: VenueRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<DataStatus<List<Venue>>> = searchQuery
        .debounce { 400 }
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            venuesRepository.getMatchingVenuesInRadius(query)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DataStatus.Success()
        )

    fun onSearch(query: String) {
        if (query.isNotEmpty()) {
            _searchQuery.value = query
        }
    }
}
