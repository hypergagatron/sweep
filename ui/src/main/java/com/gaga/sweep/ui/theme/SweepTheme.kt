package com.gaga.sweep.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


private val SweepScheme = darkColorScheme(
    primary = ElectricGreenMain,
    onPrimary = ElectricGreenOn,
    primaryContainer = ElectricGreenContainer,
    onPrimaryContainer = ElectricGreenMain,

    secondary = MutedAquaSecondary,
    onSecondary = ElectricGreenOn,

    background = DeepIndigoBackground,
    onBackground = TextHighContrast,

    surface = DeepIndigoSurface,
    onSurface = TextHighContrast,
    surfaceVariant = DeepIndigoContainer,
    onSurfaceVariant = TextLowContrast,

    error = ErrorRed,
    onError = OnErrorRed
)


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
