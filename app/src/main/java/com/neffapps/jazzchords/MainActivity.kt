package com.neffapps.jazzchords

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.lifecycleScope
import com.neffapps.jazzchords.notes.*
import com.neffapps.jazzchords.ui.theme.JazzchordsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlin.math.PI
import kotlin.math.atan2

@ExperimentalComposeUiApi
@ExperimentalUnitApi
class MainActivity : ComponentActivity() {

    private lateinit var flowTimer: FlowTimer
    private var delay: Long = 6000
    private val delays = listOf<Long>(12000, 10000, 8000, 6000, 4000, 3000, 2000, 1000, 500, 250)

    private lateinit var allFrets: List<Fret>
    private val mainViewModel by viewModels<MainViewModel>()

    private val progressions = Progressions()

    class FlowTimer {
        var active: Boolean = false
        var isPlaying: Boolean = true

        private fun tickerFlow(period: Long, initialDelay: Long = 0) = flow {
            active = true
            while (true) {
                if (active) {
                    if (!isPlaying) {
                        isPlaying = true
                        delay(initialDelay)
                    }
                    emit(Unit)
                    delay(period)
                }
            }
        }.onStart { delay(initialDelay) }

        fun start(period: Long, initialDelay: Long = 0)  =
            tickerFlow(period, initialDelay)
                .flowOn(Dispatchers.Default)

        fun pause() {
            active = false
        }

        fun stop() {
            isPlaying = false
            active = false
        }

        fun play() {
           active = true
        }
    }

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
            flowTimer.start(250, 0)
                .collect {
                    handleQuarterSecond()
                }
        }

        setContent {
            var speed by remember {
                mutableStateOf(delay)
            }

            JazzchordsTheme(darkTheme = true) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .background(com.neffapps.jazzchords.ui.theme.Anthrazit)
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
                            Content(allFrets, baseWidth, baseHeight, mainViewModel, flowTimer)
                        }
                    }
                }

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(top = 15.dp)
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
                                    delay = delays[index]
                                    speed = delay
                                    mainViewModel.resetWithDelay(delay)
                                }
                            }

                            Column {
                                Box(modifier = Modifier
                                    .padding(top = 15.dp, end = 80.dp)

                                ) {
                                    Text(
                                        modifier = Modifier.width(50.dp),
                                        text = "${speed.toFloat() / 1000.0f}s",
                                        color = Color.Yellow.copy(alpha = 0.6f)
                                    )
                                }
                            }
                            Column {
                                progressions.getFlyMeToTheMoonKey().forEach {
                                    FlyMeToTheMoonOption(
                                        viewModel = mainViewModel,
                                        it,
                                    )
                                }
                            }
                            Column {
                                progressions.getMostCommon251Keys().forEach {
                                    Selectable251Option(
                                        viewModel = mainViewModel,
                                        it,
                                    )
                                }
                            }
                            Column {
                                ChordTypes.allFamilies.forEach {
                                    SelectableChordOption(
                                        viewModel = mainViewModel,
                                        it,
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }

    private fun handleQuarterSecond() {
        mainViewModel.handleQuarterSecond()
    }
}

@ExperimentalUnitApi
@Composable
fun SelectableChordOption(
    viewModel: MainViewModel,
    chordType: ChordType,
) {
    val selected = viewModel.activatedChordFamilies[chordType.id]

    Box(modifier = Modifier
        .padding(top = 15.dp, start = 30.dp, end = 80.dp)
        .clickable {
            viewModel.toggleFamily(chordType)
        }
    ) {
        Text(
            text = chordType.title,
            color = if (selected == true)
                Color.Yellow.copy(alpha = 0.6f)
            else Color.LightGray.copy(alpha = 0.3f)
        )
    }
}

@ExperimentalUnitApi
@Composable
fun Selectable251Option(
    viewModel: MainViewModel,
    key: Key,
) {
    val selected = viewModel.activated251Key.collectAsState()

    Box(modifier = Modifier
        .padding(top = 15.dp, start = 30.dp, end = 80.dp)
        .clickable {
            viewModel.toggle251Key(key)
        }
    ) {
        Text(
            text = "2-5-1 in " + key.name,
            color = if (selected.value?.name == key.name)
                Color.Yellow.copy(alpha = 0.6f)
            else Color.LightGray.copy(alpha = 0.3f)
        )
    }
}

@ExperimentalUnitApi
@Composable
fun FlyMeToTheMoonOption(
    viewModel: MainViewModel,
    key: Key,
) {
    val selected = viewModel.activated251Key.collectAsState()

    Box(modifier = Modifier
        .padding(top = 15.dp, start = 30.dp, end = 80.dp)
        .clickable {
            viewModel.toggle251Key(key)
        }
    ) {
        Text(
            text = key.name,
            color = if (selected.value?.name == key.name)
                Color.Yellow.copy(alpha = 0.6f)
            else Color.LightGray.copy(alpha = 0.3f)
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember {
        mutableStateOf(179f)
    }
    var touchX by remember {
        mutableStateOf(0f)
    }
    var touchY by remember {
        mutableStateOf(0f)
    }
    var centerX by remember {
        mutableStateOf(0f)
    }
    var centerY by remember {
        mutableStateOf(0f)
    }

    Image(
        painter = painterResource(id = R.drawable.ic_knob),
        contentDescription = "Music knob",
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val windowBounds = it.boundsInWindow()
                centerX = windowBounds.size.width / 2f
                centerY = windowBounds.size.height / 2f
            }
            .pointerInteropFilter { event ->
                touchX = event.x
                touchY = event.y
                val angle = -atan2(centerX - touchX, centerY - touchY) * (180f / PI).toFloat()

                when (event.action) {
                    MotionEvent.ACTION_DOWN,
                    MotionEvent.ACTION_MOVE -> {
                        if (angle !in -limitingAngle..limitingAngle) {
                            val fixedAngle = if (angle in -180f..-limitingAngle) {
                                360f + angle
                            } else {
                                angle
                            }
                            rotation = fixedAngle

                            val percent = (fixedAngle - limitingAngle) / (360f - 2 * limitingAngle)
                            onValueChange(percent)
                            true
                        } else false
                    }
                    else -> false
                }
            }
            .rotate(rotation)
    )
}

@ExperimentalComposeUiApi
@ExperimentalUnitApi
@Composable
fun Content(
    frets: List<Fret>,
    baseWidth: Float,
    baseHeight: Float,
    viewModel: MainViewModel,
    flowTimer: MainActivity.FlowTimer
) {
    val chord by viewModel.currentChord.collectAsState()

    Surface(color = com.neffapps.jazzchords.ui.theme.Anthrazit) {
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
                        text = if (chord.halfNoteType == HalfNoteType.FLAT) {
                            chord.flatName
                        } else {
                            chord.name
                        },
                        fontSize = TextUnit(4.0f + baseWidth/2.0f, TextUnitType.Sp),
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = Color.White,
                        text = if (!chord.shape.isEmpty()) "${chord.shape} shape" else "",
                        fontSize = TextUnit(4.0f + baseWidth/3.0f, TextUnitType.Sp),
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
                    .padding(end = 50.dp, top = 15.dp)
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

//            val backgroundColor =
//                if (!openPosition) Color.Black
//                else com.neffapps.jazzchords.ui.theme.Anthrazit
            if (note.string == 4) {
                Box (
                    modifier = Modifier
                        .background(com.neffapps.jazzchords.ui.theme.Anthrazit)
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
    width: Float,
    baseHeight: Float,
    viewModel: MainViewModel
) {
    val chord by viewModel.currentChord.collectAsState()
    val key by viewModel.activated251Key.collectAsState()
    val backgroundColor =
        if (!openPosition) Color.Black
        else com.neffapps.jazzchords.ui.theme.Anthrazit
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
        if (note.noteInChord(chord.notes)) {
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

@ExperimentalComposeUiApi
@ExperimentalUnitApi
@Preview
@Composable
fun PhotographerCardPreview() {
    val mainViewModel = MainViewModel()
    val flowTimer = MainActivity.FlowTimer()
    JazzchordsTheme {
        Content(
            Fretboard.getAllFrets(20.0f),
            20.0f,
            20.0f,
            mainViewModel,
            flowTimer)
    }
}