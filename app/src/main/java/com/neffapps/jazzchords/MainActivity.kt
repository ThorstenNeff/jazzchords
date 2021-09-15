package com.neffapps.jazzchords

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.lifecycleScope
import com.neffapps.jazzchords.notes.*
import com.neffapps.jazzchords.timing.FlowTimer
import com.neffapps.jazzchords.ui.components.*
import com.neffapps.jazzchords.ui.theme.Anthrazit
import com.neffapps.jazzchords.ui.theme.JazzchordsTheme
import com.neffapps.jazzchords.ui.views.MainView
import kotlinx.coroutines.flow.collect

@ExperimentalComposeUiApi
@ExperimentalUnitApi
class MainActivity : ComponentActivity() {

    private lateinit var flowTimer: FlowTimer
    private var bpm: Long = 30
    private val bpmPresets = listOf<Long>(30, 40, 60, 80, 120, 140, 180, 200, 220, 240)

    private lateinit var allFrets: List<Fret>
    private val mainViewModel by viewModels<MainViewModel>()

    private val progressions = Progressions()

    private fun beatPeriod(): Long = ((60f / bpm / 4f) * 1000).toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val displayMetrics = resources.displayMetrics
        val dpHeight = displayMetrics.heightPixels / displayMetrics.density
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        val baseWidth = dpWidth / 20.0f
        val baseHeight = dpHeight / 20.0f
        allFrets = Fretboard.getAllFrets(baseWidth)

        lifecycleScope.launchWhenCreated {
            flowTimer = FlowTimer()
            flowTimer.build(beatPeriod(), 0)
                .collect {
                    if (it == 2) {
                        handleQuarterSecond()
                    } else if (it == 0) {
                        updateBeatOnly()
                    } else if (it == 1) {
                        startChords()
                    }
                }
        }

        setContent {
            var speed by remember {
                mutableStateOf(bpm)
            }

            JazzchordsTheme(darkTheme = true) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(Anthrazit)
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
                            MainView(allFrets, baseWidth, baseHeight, mainViewModel, flowTimer)
                        }
                    }
                }

                // Column withStrumArrows
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(top = 20.dp)
                    ) {
                        StrumArrows(
                            viewModel = mainViewModel,
                            width = allFrets.sumOf { it.width.toDouble() }
                        )
                    }
                }

                // Column with Film countdown
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    ) {
                        ProgressionPicker(
                            viewModel = mainViewModel,
                            progressions = progressions,
                        )
                    }
                    FilmCountdown(mainViewModel)
                }

                // Column with Preview Fretboard
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(modifier = Modifier.width((allFrets.sumOf { it.width.toDouble() }).dp)
                        .align(Alignment.CenterHorizontally)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.Start)
                                    .padding(
                                        top = 20.dp,
                                        start = 2.dp
                                    )
                            ) {
                                Column() {
                                    PreviewFretboard(
                                        frets = allFrets,
                                        viewModel = mainViewModel,
                                    )
                                }
                            }
                        }
                    }
                }

                // Column with MusicKnob
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 20.dp)
                    ) {
                        Row() {
                            Column(modifier = Modifier.fillMaxHeight()) {
                                MusicKnob(
                                    modifier = Modifier
                                        .width(70.dp)
                                        .height(70.dp)
                                        .padding(top = 20.dp)
                                ) {
                                    val index = (it * 10.0f).toInt()
                                    bpm = bpmPresets[index]
                                    speed = bpm
                                    mainViewModel.setBpm(bpm)
                                    flowTimer.setPeriod(beatPeriod())
                                }
                            }

                            Column {
                                Box(modifier = Modifier
                                    .padding(top = 20.dp, end = 20.dp)

                                ) {
                                    Text(
                                        modifier = Modifier.width(50.dp),
                                        text = "$speed bpm",
                                        color = Color.Yellow.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }

        mainViewModel.toggle251Key(progressions.getMostCommon251Keys().first())
        mainViewModel.setBpm(bpm)
    }

    private fun handleQuarterSecond() {
        mainViewModel.handleQuarterSecond()
    }

    private fun updateBeatOnly() {
        mainViewModel.updateBeatOnly()
    }

    private fun startChords() {
        mainViewModel.rewind()
        mainViewModel.handleQuarterSecond()
    }
}

@ExperimentalComposeUiApi
@ExperimentalUnitApi
@Preview
@Composable
fun PhotographerCardPreview() {
    val mainViewModel = MainViewModel()
    JazzchordsTheme {
        FilmCountdown(viewModel = mainViewModel)
    }
}