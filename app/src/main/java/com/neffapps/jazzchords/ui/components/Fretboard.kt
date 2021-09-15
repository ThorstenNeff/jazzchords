package com.neffapps.jazzchords.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.neffapps.jazzchords.MainViewModel
import com.neffapps.jazzchords.notes.Fret
import com.neffapps.jazzchords.notes.HalfNoteType
import com.neffapps.jazzchords.notes.Note
import com.neffapps.jazzchords.ui.theme.Anthrazit
import kotlinx.coroutines.flow.MutableStateFlow


@ExperimentalUnitApi
@Composable
fun FretboardView(frets: List<Fret>, baseHeight: Float, viewModel: MainViewModel) {
    Row {
        frets.forEachIndexed { index, fret ->
            FretView(index == 0, fret.notes, fret.width, baseHeight, viewModel)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun FretView(
    openPosition: Boolean,
    stringNotes: List<Note>,
    width: Float,
    baseHeight: Float,
    viewModel: MainViewModel
) {
    Column {
        for (note in stringNotes) {
            if (note.name == note.flatName) {
                viewModel.activeNotes[note]?.let {
                    FretStringView(
                        openPosition,
                        note,
                        it,
                        width,
                        baseHeight,
                        viewModel
                    )
                }
            } else {
                viewModel.activeNotes[note]?.let {
                    FretStringAccidentalView(
                        openPosition,
                        note,
                        it,
                        width,
                        baseHeight,
                        viewModel
                    )
                }
            }

            if (note.string == 4) {
                Box (
                    modifier = Modifier
                        .background(Anthrazit)
                        .width(Dp(width))
                        .height(Dp(12f))
                ){
                    if (
                        note.fret == 5
                        || note.fret == 7
                        || note.fret == 10
                        || note.fret == 12
                    )
                    {
                        // Draw white dot
                        Surface(
                            modifier = Modifier
                                .padding(top = Dp(7f))
                                .size(Dp(5.0f))
                                .align(Alignment.Center),
                            shape = CircleShape,
                            color = Color.LightGray
                        ) {
                            // No content here
                        }
                    }
                }
            }

        }
    }
}

@ExperimentalUnitApi
@Composable
fun FretStringView(
    openPosition: Boolean,
    note: Note,
    noteStateFlow: MutableStateFlow<Pair<Note, Boolean>>,
    width: Float,
    baseHeight: Float,
    viewModel: MainViewModel
) {
    val noteState by noteStateFlow.collectAsState()
    val showCurrentChord = viewModel.showCurrentChord.collectAsState(false)

    val backgroundColor =
        if (!openPosition) Color.Black
        else Anthrazit
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .width(Dp(width))
            .height(Dp(baseHeight))
    ) {
        Divider(
            color = Color.LightGray.copy(alpha = 0.5f),
            thickness = Dp( 1.0f),
            modifier = Modifier.align(Alignment.Center)
        )
        if (noteState.second && showCurrentChord.value) {
            Surface(
                modifier = Modifier
                    .size(Dp(baseHeight - 2.0f))
                    .align(Alignment.Center),
                shape = CircleShape,
                color = Color.Yellow.copy(alpha = 0.2f)
            ) {
                // No content here
            }
        }
        if (note.textVisible) {
            Text(
                note.name,
                fontSize = TextUnit(4.0f + width/4.0f, TextUnitType.Sp),
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@ExperimentalUnitApi
@Composable
fun FretStringAccidentalView(
    openPosition: Boolean,
    note: Note,
    noteStateFlow: MutableStateFlow<Pair<Note, Boolean>>,
    width: Float,
    baseHeight: Float,
    viewModel: MainViewModel
) {
    val noteState by noteStateFlow.collectAsState()
    val key by viewModel.activated251Key.collectAsState()
    val showCurrentChord = viewModel.showCurrentChord.collectAsState(false)

    val backgroundColor =
        if (!openPosition) Color.Black
        else Anthrazit
    Box(
        modifier = Modifier
            .background(backgroundColor)
            .width(Dp(width))
            .height(Dp(baseHeight))
    ) {
        Divider(
            color = Color.LightGray.copy(alpha = 0.5f),
            thickness = Dp( 1.0f),
            modifier = Modifier.align(Alignment.Center)
        )
        if (noteState.second && showCurrentChord.value) {
            Surface(
                modifier = Modifier
                    .size(Dp(baseHeight - 2.0f))
                    .align(Alignment.Center),
                shape = CircleShape,
                color = Color.Yellow.copy(alpha = 0.2f)
            ) {
                // No content here
            }
        }
        if (note.textVisible) {
            Text(
                text = if (key?.halfNoteType == HalfNoteType.FLAT) {
                    note.flatName
                } else {
                    note.name
                },
                fontSize = TextUnit(4.0f + width/4.0f, TextUnitType.Sp),
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}