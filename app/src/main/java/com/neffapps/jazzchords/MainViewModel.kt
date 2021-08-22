package com.neffapps.jazzchords

import androidx.lifecycle.ViewModel
import com.neffapps.jazzchords.notes.Chords
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    var chord = Chords.Am7

    var currentChord = MutableStateFlow(chord)
        private set

    fun switchChord() {
        chord = if (chord == Chords.Am7) Chords.Bbm7 else Chords.Am7

        currentChord.value = listOf()
        currentChord.value = chord
    }
}