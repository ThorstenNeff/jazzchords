package com.neffapps.jazzchords.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import com.neffapps.jazzchords.MainViewModel
import com.neffapps.jazzchords.R
import com.neffapps.jazzchords.notes.Fret
import com.neffapps.jazzchords.notes.HalfNoteType
import com.neffapps.jazzchords.timing.FlowTimer
import com.neffapps.jazzchords.ui.components.FretboardView
import com.neffapps.jazzchords.ui.theme.Anthrazit

@ExperimentalComposeUiApi
@ExperimentalUnitApi
@Composable
fun MainView(
    frets: List<Fret>,
    baseWidth: Float,
    baseHeight: Float,
    viewModel: MainViewModel,
    flowTimer: FlowTimer
) {
    val chord by viewModel.currentChord.collectAsState()
    val showCurrentChord = viewModel.showCurrentChord.collectAsState(false)

    Surface(color = Anthrazit) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White,
                        text = when {
                            !showCurrentChord.value -> {
                                ""
                            }
                            chord.halfNoteType == HalfNoteType.FLAT -> {
                                chord.flatName
                            }
                            else -> {
                                chord.name
                            }
                        },
                        fontSize = TextUnit(4.0f + baseWidth / 2.0f, TextUnitType.Sp),
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White,
                        text = if (!showCurrentChord.value) {
                            ""
                        } else if (!chord.shape.isEmpty()) {
                            "${chord.shape} shape"
                        } else {
                            ""
                        },
                        fontSize = TextUnit(4.0f + baseWidth / 3.0f, TextUnitType.Sp),
                    )
                }
            }
            Row(
                modifier = Modifier.height(Dp(70.0f))
            ) {}
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Surface(color = Color.Black) {
                    FretboardView(frets, baseHeight, viewModel)
                }
            }
            Row(
                modifier = Modifier.height(Dp(120.0f))
            ) {}
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Box(modifier = Modifier
                    .padding(end = 50.dp, top = 15.dp)
                    .clickable {
                        viewModel.rewind()
                    }
                ) {
                    Image(
                        modifier = Modifier.size(50.dp, 50.dp),
                        painter = painterResource(id = R.drawable.ic_backtostart),
                        contentDescription = "Replay"
                    )
                }
                Box(modifier = Modifier
                    .padding(end = 50.dp, top = 15.dp)
                    .clickable {
                        viewModel.stepBack()
                        flowTimer.pause()
                    }
                ) {
                    Image(
                        modifier = Modifier.size(50.dp, 50.dp),
                        painter = painterResource(id = R.drawable.ic_backward),
                        contentDescription = "Backwards"
                    )
                }
                Box(modifier = Modifier
                    .padding(end = 50.dp, top = 15.dp)
                    .clickable {
                        viewModel.rewind()
                        flowTimer.stop()
                    }
                ) {
                    Image(
                        modifier = Modifier.size(50.dp, 50.dp),
                        painter = painterResource(id = R.drawable.ic_stop),
                        contentDescription = "Stop"
                    )
                }
                Box(modifier = Modifier
                    .padding(end = 50.dp, top = 15.dp)
                    .clickable {
                        flowTimer.pause()
                    }
                ) {
                    Image(
                        modifier = Modifier.size(50.dp, 50.dp),
                        painter = painterResource(id = R.drawable.ic_pause),
                        contentDescription = "Pause"
                    )
                }
                Box(modifier = Modifier
                    .padding(end = 50.dp, top = 15.dp)
                    .clickable {
                        flowTimer.play()
                    }
                ) {
                    Image(
                        modifier = Modifier.size(50.dp, 50.dp),
                        painter = painterResource(id = R.drawable.ic_play),
                        contentDescription = "Play"
                    )
                }
                Box(modifier = Modifier
                    .padding(top = 15.dp)
                    .clickable {
                        viewModel.stepForward()
                        flowTimer.pause()
                    }
                ) {
                    Image(
                        modifier = Modifier.size(50.dp, 50.dp),
                        painter = painterResource(id = R.drawable.ic_forward),
                        contentDescription = "Forward"
                    )
                }
            }
        }
    }
}