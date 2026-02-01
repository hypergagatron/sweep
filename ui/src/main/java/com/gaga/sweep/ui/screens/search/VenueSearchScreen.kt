package com.gaga.sweep.ui.screens.search

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.ui.R
import com.gaga.sweep.ui.elements.RadarAnimation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueSearchResultsScreen(
    uiState: VenueSearchResultsUiState,
    events: VenueSearchResultsEvents
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val permissionsToRequest = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    val allPermissionsGranted = permissionsToRequest.all { permission ->
        ContextCompat.checkSelfPermission(
            LocalContext.current,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val isGranted = permissions.values.all { it }
        if (isGranted) {
            events.onLocationPermissionGiven()
        } else {
            events.onLocationPermissionRejected()
        }
    }

    LaunchedEffect(true) {
        if (!allPermissionsGranted) {
            launcher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            events.onLocationPermissionGiven()
        }
    }

    var query by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                SearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = query,
                            onQueryChange = { query = it },
                            onSearch = {
                                keyboardController?.hide()
                                events.onSearchTermChanged(it)
                            },
                            expanded = false,
                            onExpandedChange = { },
                            placeholder = { Text(stringResource(R.string.search_placeholder)) },
                        )
                    },
                    expanded = false,
                    onExpandedChange = { },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) { }

                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 2.dp)

                if (uiState.results is DataStatus.Failure) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(24.dp)
                            .background(MaterialTheme.colorScheme.error)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            text = uiState.results.exception.message + if (!uiState.results.cachedData.isNullOrEmpty()) ", showing cached data" else "",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onError
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            when (uiState.results) {
                is DataStatus.Loading -> {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        RadarAnimation(modifier = Modifier.size(100.dp), speed = 2)
                    }
                }

                is DataStatus.Success, is DataStatus.Failure -> {
                    val venues = when (uiState.results) {
                        is DataStatus.Success -> uiState.results.data
                        is DataStatus.Failure -> uiState.results.cachedData
                        else -> listOf()
                    }

                    if (!venues.isNullOrEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(
                                venues.size
                            ) { venueIndex ->
                                VenueItem(
                                    venues[venueIndex],
                                    events.onVenueClick
                                )
                            }
                        }
                    } else {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(stringResource(R.string.search_no_results))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VenueItem(
    venue: Venue,
    onVenueClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVenueClick(venue.id) }) {

        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(text = venue.name ?: "", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
