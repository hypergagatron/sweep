package com.gaga.sweep.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.gaga.sweep.ui.screens.search.VenueSearchResultsEvents
import com.gaga.sweep.ui.screens.search.VenueSearchResultsScreen
import com.gaga.sweep.ui.screens.search.VenueSearchResultsUiState
import com.gaga.sweep.ui.screens.search.VenueSearchViewModel
import com.gaga.sweep.ui.screens.start.StartScreen
import com.gaga.sweep.ui.screens.venueDetails.VenueDetailsEvents
import com.gaga.sweep.ui.screens.venueDetails.VenueDetailsScreen
import com.gaga.sweep.ui.screens.venueDetails.VenueDetailsUiState
import com.gaga.sweep.ui.screens.venueDetails.VenueDetailsViewModel
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun Navigation() {
    val backStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(NavKey::class) {
                    subclass(
                        ScreenKey.Start::class,
                        ScreenKey.Start.serializer()
                    )
                    subclass(
                        ScreenKey.SearchResults::class,
                        ScreenKey.SearchResults.serializer()
                    )
                    subclass(
                        ScreenKey.VenueDetails::class,
                        ScreenKey.VenueDetails.serializer()
                    )
                }
            }
        }, ScreenKey.Start
    )

    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = { key ->
            when (key) {
                is ScreenKey.Start -> {
                    NavEntry(key) {
                        StartScreen(onSearch = {
                            backStack.add(ScreenKey.SearchResults)
                        })
                    }
                }

                is ScreenKey.SearchResults -> {
                    NavEntry(
                        key,
                        metadata = NavDisplay.transitionSpec {
                            fadeIn(animationSpec = tween(500)) togetherWith
                                    fadeOut(animationSpec = tween(500))
                        } + NavDisplay.popTransitionSpec {
                            fadeIn(animationSpec = tween(500)) togetherWith
                                    fadeOut(animationSpec = tween(500))
                        } + NavDisplay.predictivePopTransitionSpec {
                            fadeIn(animationSpec = tween(500)) togetherWith
                                    fadeOut(animationSpec = tween(500))
                        }
                    ) {
                        val viewModel: VenueSearchViewModel = hiltViewModel()
                        val searchResults by viewModel.searchResults.collectAsStateWithLifecycle()
                        val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

                        val events = remember(viewModel) {
                            VenueSearchResultsEvents(
                                onSearchTermChanged = { searchTerm ->
                                    viewModel.onSearch(
                                        searchTerm
                                    )
                                },
                                onLocationPermissionGiven = {},
                                onLocationPermissionRejected = {},
                                onVenueClick = { id -> backStack.add(ScreenKey.VenueDetails(id)) }
                            )
                        }

                        VenueSearchResultsScreen(
                            uiState = VenueSearchResultsUiState(searchResults, searchQuery),
                            events = events
                        )
                    }
                }

                is ScreenKey.VenueDetails -> {
                    NavEntry(key) {
                        val viewModel: VenueDetailsViewModel = hiltViewModel()
                        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

                        LaunchedEffect(key.id) {
                            viewModel.getVenue(key.id)
                        }

                        VenueDetailsScreen(
                            VenueDetailsUiState(uiState),
                            VenueDetailsEvents(
                                onBack = {
                                    if (backStack.size > 1) {
                                        backStack.removeAt(backStack.lastIndex)
                                    }
                                }
                            )
                        )
                    }
                }

                else -> error("Unknown navkey $key")
            }
        }
    )
}
