package com.gaga.sweep.ui.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.gaga.sweep.ui.R
import com.gaga.sweep.ui.elements.RadarAnimation

@Composable
fun StartScreen(
    onSearch: () -> Unit
) {

    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.start_title),
            style = MaterialTheme.typography.displayLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(16.dp))

        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 64.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.start_description),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(24.dp))

        RadarAnimation(
            isAnimating = true,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(200.dp),
            speed = 1
        )

        Spacer(Modifier.height(40.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = onSearch
        ) {
            Text(stringResource(R.string.start_cta))
        }
    }
}
