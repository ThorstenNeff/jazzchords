package com.neffapps.jazzchords

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.neffapps.jazzchords.notes.*
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    // TODO: Add interval slider

    private var chords = mutableListOf<Chord>().also {
        it.addAll(Dominant7Chords().getDominant7Chords())
    }

    private val chordFamilies = ChordFamilies.allFamilies

    val currentChord = MutableStateFlow(chords[0])

    val activatedChordFamilies = mutableStateMapOf(
            Pair(chordFamilies[0].id, true)
        )

    fun switchChord() {
        chords.let {
            val currentChordIndex = rand(0, it.size - 1)
            currentChord.value = it[currentChordIndex]
        }
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
        chords.clear()
        activatedChordFamilies.filter {
            it.value
        }.map { it.key }.toList().forEach {  familyName ->
            chordFamilies
                .filter { familyName == it.id }
                .toList()
                .forEach {
                    chords.addAll(it.chords)
                }
        }
        Log.d("TEST", "updateChords ${chords.size}")
    }
}