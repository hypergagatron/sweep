package com.gaga.sweep.ui.elements

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.gaga.sweep.ui.theme.DeepIndigoBackground
import com.gaga.sweep.ui.theme.ElectricGreenMain
import java.util.Random
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun RadarDots(modifier: Modifier = Modifier.Companion) {
    Box(
        modifier = modifier
            .background(DeepIndigoBackground, CircleShape)
            .clip(CircleShape)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val random = Random(43)
            repeat(20) {
                drawCircle(
                    color = ElectricGreenMain,
                    radius = 2.dp.toPx(),
                    center = Offset(
                        x = random.nextFloat() * size.width,
                        y = random.nextFloat() * size.height
                    )
                )
            }
        }
    }
}

@Composable
fun RadarSweep(
    modifier: Modifier = Modifier.Companion
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Rotation")
    val rotationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "Angle"
    )

    val colors = listOf(
        Color(0xAA000000),
        Color(0x88000000),
        Color(0x4400FF9C),
        Color(0x8800FF9C),
        Color(0xFF00FF9C),
    )

    Box(
        modifier = modifier
            .graphicsLayer { rotationZ = rotationAngle }
            .background(
                brush = Brush.sweepGradient(colors = colors),
                shape = CircleShape
            )
    )
}

@Composable
fun RadarGrid(
    modifier: Modifier = Modifier.Companion,
    gridColor: Color = ElectricGreenMain,
    ringCount: Int = 4,
    lineCount: Int = 8
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val centerX = canvasWidth / 2
        val centerY = canvasHeight / 2
        val maxRadius = minOf(canvasWidth, canvasHeight) / 2

        for (i in 1..ringCount) {
            drawCircle(
                color = gridColor,
                radius = maxRadius * (i.toFloat() / ringCount),
                center = Offset(centerX, centerY),
                style = Stroke(width = 2.dp.toPx()),
                alpha = 0.5f
            )
        }

        for (i in 0 until lineCount) {
            val angleInDegrees = i * (360f / lineCount)
            val angleInRadians = Math.toRadians(angleInDegrees.toDouble()).toFloat()

            val lineEnd = Offset(
                x = centerX + maxRadius * cos(angleInRadians),
                y = centerY + maxRadius * sin(angleInRadians)
            )

            drawLine(
                color = gridColor,
                start = Offset(centerX, centerY),
                end = lineEnd,
                strokeWidth = 1.dp.toPx(),
                alpha = 0.5f
            )
        }
    }
}

@Composable
fun RadarAnimation(
    isAnimating: Boolean = true,
    modifier: Modifier = Modifier.Companion
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {

        RadarDots(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )

        if (isAnimating) {
            RadarSweep(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f)
            )
        }

        RadarGrid(
            modifier = Modifier
                .fillMaxSize()
                .aspectRatio(1f)
        )
    }
}
