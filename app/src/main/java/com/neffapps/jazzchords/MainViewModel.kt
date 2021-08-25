package com.neffapps.jazzchords

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.neffapps.jazzchords.notes.*
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    // TODO: Add interval slider

    private var chords = Dominant7Chords().getDominant7Chords()

    private val chordFamilies = ChordFamilies.allFamilies

    val currentChord = MutableStateFlow(chords[0])

    val activatedChordFamilies = mutableStateMapOf(
            Pair(chordFamilies[0].id, true)
        )

    fun switchChord() {
        val currentChordIndex = rand(0, chords.size - 1)
        currentChord.value = chords[currentChordIndex]
    }

    private fun rand(start: Int, end: Int): Int {
        require(start <= end) { "Illegal Argument" }
        return (Math.random() * (end - start + 1)).toInt() + start
    }

    fun toggleFamily(chordFamily: ChordFamily) {
        val flag: Boolean = activatedChordFamilies[chordFamily.id] ?: false
        activatedChordFamilies[chordFamily.id] = !flag
        updateChords()
    }

    private fun updateChords() {

    }
}