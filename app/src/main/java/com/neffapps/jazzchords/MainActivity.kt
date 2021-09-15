package com.neffapps.jazzchords

import android.os.Bundle
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.lifecycle.lifecycleScope
import com.neffapps.jazzchords.notes.*
import com.neffapps.jazzchords.strums.StrumType
import com.neffapps.jazzchords.timing.FlowTimer
import com.neffapps.jazzchords.ui.theme.Anthrazit
import com.neffapps.jazzchords.ui.theme.JazzchordsTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.max

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
                            .align(Alignment.Start)
                            .padding(top = 20.dp)
                    ) {
                        StrumArrows(
                            viewModel = mainViewModel,
                            width = allFrets.sumOf { it.width.toDouble() }
                        )
                    }
                }

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

@Composable
fun ProgressionPicker(
    viewModel: MainViewModel,
    progressions: Progressions,
) {
    val selected = viewModel.activated251Key.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(viewModel.current251Index) }
    val items = progressions.getMostCommon251Keys()

    Box(modifier = Modifier
        .padding(20.dp)
        .width(300.dp)
        .wrapContentSize(Alignment.TopCenter)) {
        Text(selected.value?.name ?: "",modifier = Modifier
            .height(50.dp)
            .clickable(onClick = { expanded = true })
            .background(
                com.neffapps.jazzchords.ui.theme.Anthrazit
            )
            , Color.Yellow.copy(alpha = 0.6f)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    com.neffapps.jazzchords.ui.theme.Anthrazit
                )
        ) {
            items.forEachIndexed { index, key ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    viewModel.toggle251Key(key)
                }) {
                    Text(
                        text = key.name,
                        color = if (selected.value?.name == key.name)
                            Color.Yellow.copy(alpha = 0.6f)
                        else Color.LightGray.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun StrumArrows(viewModel: MainViewModel, width: Double) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy((-4).dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(Dp(width.toFloat()))
        ) {
            StrumArrow(viewModel, 0, StrumType.DOWN)
            StrumArrow(viewModel, 1, StrumType.UP)
            Spacer(modifier = Modifier.width(15.dp))
            StrumArrow(viewModel, 2, StrumType.DOWN)
            StrumArrow(viewModel, 3, StrumType.UP)
            Spacer(modifier = Modifier.width(15.dp))
            StrumArrow(viewModel, 4, StrumType.DOWN)
            StrumArrow(viewModel, 5, StrumType.UP)
            Spacer(modifier = Modifier.width(15.dp))
            StrumArrow(viewModel, 6, StrumType.DOWN)
            StrumArrow(viewModel, 7, StrumType.UP)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun StrumArrow(
    viewModel: MainViewModel,
    index: Int,
    type: StrumType
) {
    val beat = viewModel.eightNoteBeatIndex.collectAsState()

    val drawable = when (type) {
        StrumType.DOWN -> ImageVector.vectorResource(id = R.drawable.ic_uparrow)
        StrumType.UP -> ImageVector.vectorResource(id = R.drawable.ic_downarrow)
        StrumType.MISS -> ImageVector.vectorResource(id = R.drawable.ic_miss)
    }
    Icon(
        imageVector = drawable,
        contentDescription = "",
        modifier = Modifier.size(24.dp, 60.dp),
        tint = if (beat.value < 0) {
            Anthrazit
        } else if (beat.value >= index) {
            Color.LightGray
        } else {
            Color.Black
        }
    )
}

@ExperimentalComposeUiApi
@Composable
fun MusicKnob(
    modifier: Modifier = Modifier,
    limitingAngle: Float = 25f,
    onValueChange: (Float) -> Unit
) {
    var rotation by remember {
        mutableStateOf(40f)
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
    flowTimer: FlowTimer
) {
    val chord by viewModel.currentChord.collectAsState()
    val showCurrentChord = viewModel.showCurrentChord.collectAsState(false)
    val beat = viewModel.eightNoteBeatIndex.collectAsState()

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
                        .background(com.neffapps.jazzchords.ui.theme.Anthrazit)
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
    val showCurrentChord = viewModel.showCurrentChord.collectAsState(false)

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
        if (note.noteInChord(chord.notes) && showCurrentChord.value) {
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

@ExperimentalComposeUiApi
@ExperimentalUnitApi
@Preview
@Composable
fun PhotographerCardPreview() {
    val mainViewModel = MainViewModel()
    val flowTimer = FlowTimer()
    JazzchordsTheme {
        FilmCountdown(viewModel = mainViewModel)
    }
}