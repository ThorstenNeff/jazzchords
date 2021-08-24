package com.neffapps.jazzchords

import androidx.lifecycle.ViewModel
import com.neffapps.jazzchords.notes.*
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    // TODO: Add interval slider

    private var chords = Dim7Chords().getDim7Chords()
        // HalfDim7Chords().getMinor7B5Chords()
        //Major7Chords().getMajor7Chords()
        // Minor7Chords().getMinor7Chords()
    // Dominant7Chords().getDominant7Chords()

    var currentChord = MutableStateFlow(chords[0])
        private set

    fun switchChord() {
        val currentChordIndex = rand(0, chords.size - 1)
        currentChord.value = chords[currentChordIndex]
    }

    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }
}