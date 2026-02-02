package com.gaga.sweep.ui.screens.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.domain.repositories.VenueRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VenueSearchViewModel @Inject constructor(
    private val venuesRepository: VenueRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val searchQuery = savedStateHandle.getStateFlow("search_query", "")
    private val refreshTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchResults: StateFlow<DataStatus<List<Venue>>> =
        combine(searchQuery, refreshTrigger.onStart { emit(Unit) }) { query, _ -> query }
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
        savedStateHandle["search_query"] = query
        refreshTrigger.tryEmit(Unit)
    }
}
