package com.neffapps.jazzchords

import android.os.Bundle
import android.os.Handler
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
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
    private lateinit var allFrets: List<Fret>
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

        val displayMetrics = resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val baseWidth = dpWidth / 20.0f
        val baseHeight = dpHeight / 20.0f
        allFrets = Fretboard.getAllFrets(baseWidth)

        setContent {
            JazzchordsTheme(darkTheme = true) {
                Box(
                    modifier = Modifier
                        .background(Color.DarkGray)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.Center),
                            // .offset(x = Dp(-baseWidth), y = Dp(0.0f)),
                        color = Color.Blue,
                    ) {
                        // A surface container using the 'background' color from the theme
                        Content(allFrets, baseHeight, mainViewModel)
                    }
                }

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
fun Content(frets: List<Fret>, baseHeight: Float, viewModel: MainViewModel) {

    Surface(color = Color.Black) {
        FretboardView(frets, baseHeight, viewModel)
    }
}

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
            FretStringView(openPosition, note, width, baseHeight, viewModel)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun FretStringView(
    openPosition: Boolean,
    note: Note,
    width: Float,
    baseHeight: Float,
    viewModel: MainViewModel
) {
    val chordNotes: List<Note> by viewModel.currentChord.collectAsState()
    val backgroundColor = if (openPosition) Color.DarkGray else Color.Black
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
        if (note.noteInChord(chordNotes)) {
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
                text = note.name,
                fontSize = TextUnit(4.0f + width/4.0f, TextUnitType.Sp),
                color = Color.LightGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@ExperimentalUnitApi
@Preview
@Composable
fun PhotographerCardPreview() {
    val mainViewModel = MainViewModel()
    JazzchordsTheme {
        Content(Fretboard.getAllFrets(20.0f), 20.0f, mainViewModel)
    }
}