package com.gaga.sweep.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SweepTheme(content: @Composable () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = SweepScheme.background
    ) {
        MaterialTheme(
            colorScheme = SweepScheme,
            typography = SweepTypography,
            content = content
        )
    }
}
