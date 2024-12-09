package com.neffapps.jazzchords.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.neffapps.jazzchords.MainViewModel
import com.neffapps.jazzchords.ui.theme.Anthrazit
import kotlinx.coroutines.flow.map

@ExperimentalUnitApi
@Composable
fun FilmCountdown(
    viewModel: MainViewModel
) {
    //val beat = MutableStateFlow(4)
    //val showCurrentChord = MutableStateFlow(false)

    val quarterNoteBeatIndex = viewModel.eightNoteBeatIndex.map {
        if (it.toInt() != -1) {
            it / 2
        } else {
            it
        }
    }.collectAsState(initial = -1)

    val duration = viewModel.quarterNoteDuration.collectAsState()
    val showCurrentChord = viewModel.showCurrentChord.collectAsState()

    var currentRotation by remember { mutableStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(quarterNoteBeatIndex.value) {
        if (!showCurrentChord.value && quarterNoteBeatIndex.value.toInt() >= 0) {
            rotation.animateTo(
                targetValue = 0f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(1),
                    repeatMode = RepeatMode.Restart
                )
            )
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = repeatable(
                    iterations = 1,
                    animation = tween(duration.value.toInt() / 2, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            ) {
                currentRotation = value
            }
        }
    }

    if (!showCurrentChord.value && quarterNoteBeatIndex.value >= 0) {

        // Infinite repeatable rotation when is playing
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .size(width = 150.dp, height = 150.dp)
                    .align(Alignment.CenterHorizontally)
                    .drawBehind {
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(Color.LightGray, Anthrazit),
                                center = Offset.Unspecified,
                            ),
                            size = size
                        )
                        drawLine(
                            color = Color.Black,
                            start = Offset(0f, center.y),
                            end = Offset(size.width, center.y),
                            strokeWidth = 3.dp.toPx()
                        )
                        drawLine(
                            color = Color.Black,
                            start = Offset(center.x, 0f),
                            end = Offset(center.x, size.height),
                            strokeWidth = 3.dp.toPx()
                        )
                        drawCircle(
                            color = Color.LightGray,
                            radius = size.width / 2f - 32f,
                            center = center,
                            style = Stroke(width = 2f)
                        )
                        drawCircle(
                            color = Color.LightGray,
                            radius = size.width / 2f - 52f,
                            center = center,
                            style = Stroke(width = 2f)
                        )
                        rotate(
                            degrees = rotation.value,
                        ) {
                            drawLine(
                                start = center,
                                end = Offset(center.x, 0f),
                                color = Color.Black,
                                strokeWidth = 2.0f
                            )
                        }
                    }
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = when {
                        !showCurrentChord.value -> {
                            if (quarterNoteBeatIndex.value > -1) {
                                "${(quarterNoteBeatIndex.value) + 1}"
                            } else {
                                ""
                            }
                        }
                        else -> ""
                    },
                    fontSize = TextUnit(48.0f, TextUnitType.Sp),
                )
            }
        }
    }
}