package com.neffapps.jazzchords

import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.neffapps.jazzchords.notes.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

class MainViewModel: ViewModel() {

    private var chords = mutableListOf<Chord>().also {
        it.addAll(Progressions().Ascending251Cmaj7.chords)
    }

    private val chordFamilies = ChordTypes.allFamilies

    val currentChord = MutableStateFlow(chords[0])

    val events = MutableStateFlow<MainEvent?>(null)

    val activated251Key = MutableStateFlow<Key?>(Progressions().Ascending251Cmaj7)
    var current251Index = 0

    val activatedChordFamilies = mutableStateMapOf(
            Pair(chordFamilies[0].id, false)
        )


    fun switchChord() {
        activated251Key.value?.let {
            current251Index++
            if (current251Index > chords.size) current251Index = 0
            if (current251Index > -1 && current251Index < chords.size) {
                currentChord.value = it.chords[current251Index]
            }
        } ?: run {
            chords.let {
                val currentChordIndex = rand(0, it.size - 1)
                if (currentChordIndex > -1) {
                    currentChord.value = it[currentChordIndex]
                }
            }
        }
    }

    private fun rand(start: Int, end: Int): Int {
        if (start > end) return -1
        return (Math.random() * (end - start + 1)).toInt() + start
    }

    fun toggleFamily(chordType: ChordType) {
        // Deactivate key, if activated
        activated251Key.value = null

        val flag: Boolean = activatedChordFamilies[chordType.id] ?: false
        activatedChordFamilies[chordType.id] = !flag
        updateChords()
    }

    fun toggle251Key(key: Key) {
        // Deactivate all families
        activatedChordFamilies.keys.forEach {
            activatedChordFamilies[it] = false
        }
        // Activate key
        activated251Key.value = key
        updateChords()
    }

    private fun updateChords() {
        chords.clear()

        activated251Key.value?.let {
            chords.addAll(it.chords)
        } ?: run {
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

    fun reset251() {
        activated251Key.value?.let {
            current251Index = -1
            currentChord.value = Chord("","", listOf(), "")
            viewModelScope.launch {
                events.emit(MainEvent.Rewind)
            }
        }

    }
}

sealed class MainEvent {
    object Rewind: MainEvent()
}