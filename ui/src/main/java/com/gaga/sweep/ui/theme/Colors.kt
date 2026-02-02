package com.gaga.sweep.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

val DeepIndigoBackground = Color(0xFF0D0E1C)
val DeepIndigoSurface = Color(0xFF16172B)
val DeepIndigoContainer = Color(0xFF1F2140)

val ElectricGreenMain = Color(0xFF00FF9C)
val ElectricGreenOn = Color(0xFF003920)
val ElectricGreenContainer = Color(0xFF005230)

val MutedAquaSecondary = Color(0xFF86D9B1)
val TextHighContrast = Color(0xFFE3E1EC)
val TextLowContrast = Color(0xFFC7C5D0)
val ErrorRed = Color(0xFFFFB4AB)
val OnErrorRed = Color(0xFF690005)

val SweepScheme = darkColorScheme(
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
