package com.neffapps.jazzchords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.neffapps.jazzchords.ui.theme.JazzchordsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JazzchordsTheme {
                // A surface container using the 'background' color from the theme
                Content()
            }
        }
    }
}

data class Note(val fret: Int, val string: Int, val name: String, val pressed: Boolean = false)

val notes = listOf<Note>(
    Note(0,1,"A"),
    Note(0,2,"E"),
    Note(0,3,"C"),
    Note(0,4,"G"),

    Note(1,1,"Bb"),
    Note(1,2,"F"),
    Note(1,3,"C#"),
    Note(1,4,"G#"),

    Note(2,1,"B"),
    Note(2,2,"F#"),
    Note(2,3,"D"),
    Note(2,4,"A"),
)

@Composable
fun Content() {
    Surface(color = Color.Black) {
        Fretboard()
    }
}

@Composable
fun Fretboard() {
    Row {
        (0 until 3).asIterable().forEach { fret ->
            Fret(notes.filter { note -> note.fret == fret }.toList())
        }
    }
}

@Composable
fun Fret(stringNotes: List<Note>, baseWidth: Float = 40.0f) {
    Column {
        for (note in stringNotes) {
            FretString(note.name, baseWidth)
        }
    }
}

@Composable
fun FretString(text: String = "*", baseWidth:Float = 40.0f) {
    Box(
        modifier = Modifier.width(Dp(baseWidth))
    ) {
        Text(
            text = text,
            color = Color.LightGray,
            modifier = Modifier.align(Alignment.Center)
        )
        Divider(
            color = Color.LightGray,
            thickness = Dp(0.2f),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun PhotographerCardPreview() {
    JazzchordsTheme {
        Content()
    }
}