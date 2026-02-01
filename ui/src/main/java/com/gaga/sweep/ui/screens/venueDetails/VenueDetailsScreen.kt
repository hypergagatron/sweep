package com.gaga.sweep.ui.screens.venueDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gaga.sweep.domain.DataStatus
import com.gaga.sweep.domain.models.Venue
import com.gaga.sweep.ui.R

data class VenueDetailsUiState(
    val venueStatus: DataStatus<Venue>
)

data class VenueDetailsEvents(
    val onBack: () -> Unit
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VenueDetailsScreen(
    uiState: VenueDetailsUiState,
    events: VenueDetailsEvents
) {
    val scrollState = rememberScrollState()

    if (uiState.venueStatus is DataStatus.Success) {
        uiState.venueStatus.data?.apply {
            val images = imageUrls
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
            ) {

                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = events.onBack) {
                            Icon(painterResource(R.drawable.ic_back), contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent
                    )
                )

                Text(
                    text = name ?: "",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displayLarge
                )

                if (!images.isNullOrEmpty()) {
                    HorizontalMultiBrowseCarousel(
                        state = rememberCarouselState { images.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp),
                        preferredItemWidth = 300.dp,
                        itemSpacing = 8.dp,
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) { index ->
                        AsyncImage(
                            model = images[index],
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .maskClip(MaterialTheme.shapes.large)
                        )
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    InfoItem(address)
                    InfoItem(hours)
                    InfoItem(contact)
                }
            }
        }
    }
}

@Composable
private fun InfoItem(text: String?) {
    if (text != null) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 12.dp)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
