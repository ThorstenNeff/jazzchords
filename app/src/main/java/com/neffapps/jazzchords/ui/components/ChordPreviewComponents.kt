package com.neffapps.jazzchords.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.neffapps.jazzchords.MainViewModel
import com.neffapps.jazzchords.notes.Fret
import com.neffapps.jazzchords.notes.Note
import com.neffapps.jazzchords.ui.theme.Anthrazit
import kotlin.math.max

@ExperimentalUnitApi
@Composable
fun PreviewFretboard(
    frets: List<Fret>,
    viewModel: MainViewModel,
) {
    val showCurrentChord = viewModel.showCurrentChord.collectAsState(false)
    val chord by viewModel.nextChord.collectAsState()

    if (chord.notes.isNotEmpty()) {
        var lowestFret = max(chord.notes.minOf { it.fret }, 1)
        var highestFret = chord.notes.maxOf { it.fret }
        if (lowestFret <= 5 && highestFret <= 5) {
            lowestFret = 1
            highestFret = 5
        } else {
            if (highestFret - lowestFret < 4) {
                highestFret = lowestFret + 4
            }
        }

        if (showCurrentChord.value) {
            Row(
                modifier = Modifier.padding(top = 120.dp,)
            ) {
                Text(
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    color = Color.LightGray,
                    text = "Next: ${chord.name} (${chord.shape}-Shape)",
                )
            }
            Row(
                modifier = Modifier.padding(top = 5.dp)
            ) {
                for (i in lowestFret..highestFret) {
                    val fret = frets[i]
                    PreviewFretView(i, fret.notes, viewModel)
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun PreviewFretView(
    fretNumber: Int,
    stringNotes: List<Note>,
    viewModel: MainViewModel,
) {
    val width = 30.dp

    Column {
        for (note in stringNotes) {
            PreviewFretStringView(note, viewModel)

            if (note.string == 4) {
                Box (
                    modifier = Modifier
                        .background(Anthrazit)
                        .width(width)
                        .height(Dp(20f))
                ){
                    Text(
                        modifier = Modifier
                            .padding(top = Dp(7f))
                            .align(Alignment.Center),
                        fontSize = TextUnit(12f, TextUnitType.Sp),
                        color = Color.LightGray,
                        text = "$fretNumber",
                    )
                }
            }
        }
    }
}


@ExperimentalUnitApi
@Composable
fun PreviewFretStringView(
    note: Note,
    viewModel: MainViewModel,
) {
    val chord by viewModel.nextChord.collectAsState()
    val backgroundColor = Anthrazit
    val width = 30.dp
    val height = 20.dp

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .width(width)
            .height(height)
    ) {
        Divider(
            color = Color.LightGray.copy(alpha = 0.5f),
            thickness = Dp( 1.0f),
            modifier = Modifier.align(Alignment.Center)
        )
        if (note.noteInChord(chord.notes)) {
            Surface(
                modifier = Modifier
                    .size(height - 2.dp)
                    .align(Alignment.Center),
                shape = CircleShape,
                color = Color.Blue.copy(alpha = 0.2f)
            ) {
                // No content here
            }
        }
    }
}