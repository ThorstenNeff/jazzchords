package com.neffapps.jazzchords

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.neffapps.jazzchords.notes.Fret
import com.neffapps.jazzchords.notes.Fretboard
import com.neffapps.jazzchords.notes.Note
import com.neffapps.jazzchords.ui.theme.JazzchordsTheme

class MainActivity : ComponentActivity() {

    private val delay: Long = 8000
    private val allFrets = Fretboard.AllFrets
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var handler: Handler

    private val switchChordsRunnable = Runnable {
        switchChords()
    }

    private fun switchChords() {
        mainViewModel.switchChord()

        handler.removeCallbacks(switchChordsRunnable)
        handler.postDelayed(switchChordsRunnable, delay)
    }

    @ExperimentalUnitApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler = Handler(mainLooper)
        setContent {
            JazzchordsTheme {
                // A surface container using the 'background' color from the theme
                Content(allFrets, mainViewModel)
            }
        }
    }

    override fun onPostResume() {
        super.onPostResume()
        switchChords()
    }
}

@ExperimentalUnitApi
@Composable
fun Content(frets: List<Fret>, viewModel: MainViewModel) {

    Surface(color = Color.Black) {
        FretboardView(frets, viewModel)
    }
}

@ExperimentalUnitApi
@Composable
fun FretboardView(frets: List<Fret>, viewModel: MainViewModel) {
    Row {
        frets.forEach { fret ->
            FretView(fret.notes, fret.width, viewModel)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun FretView(stringNotes: List<Note>, width: Float, viewModel: MainViewModel) {
    Column {
        for (note in stringNotes) {
            FretStringView(note, width, viewModel)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun FretStringView(note: Note, width: Float, viewModel: MainViewModel) {
    val chordNotes: List<Note> by viewModel.currentChord.collectAsState()

    Box(
        modifier = Modifier
            .width(Dp(width))
            .height(Dp(20.0f))
    ) {
        if (note.noteInChord(chordNotes)) {
            Surface(
                modifier = Modifier.size(20.dp).align(Alignment.Center),
                shape = CircleShape,
                color = Color.Yellow.copy(alpha = 0.2f)
            ) {
                // No content here
            }
        }
        if (note.textVisible) {
            Text(
                text = note.name,
                fontSize = TextUnit(4.0f + width/4.0f, TextUnitType.Sp),
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Divider(
            color = Color.LightGray,
            thickness = Dp(0.2f),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@ExperimentalUnitApi
@Preview
@Composable
fun PhotographerCardPreview() {
    val mainViewModel = MainViewModel()
    JazzchordsTheme {
        Content(Fretboard.AllFrets, mainViewModel)
    }
}