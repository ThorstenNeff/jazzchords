package com.neffapps.jazzchords

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import com.neffapps.jazzchords.notes.*
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {
    private val progressions = Progressions()

    private var chords = mutableListOf<Chord>().also {
        it.addAll(progressions.Ascending251Cmaj7.chords)
    }

    private val chordFamilies = ChordTypes.allFamilies
    val currentChord = MutableStateFlow(chords[0])
    val nextChord = MutableStateFlow(progressions.emptyChord)
    val activeNotes = mutableMapOf<Note, MutableStateFlow<Pair<Note, Boolean>>>()

    val showCurrentChord = MutableStateFlow(false)
    val activated251Key = MutableStateFlow<Key?>(Progressions().getMostCommon251Keys().first())
    val eightNoteBeatIndex = MutableStateFlow<Long>(-1)

    val quarterNoteDuration = MutableStateFlow<Long>(1000)

    val bpm = MutableStateFlow<Long>(60)

    var current251Index = 0

    val activatedChordFamilies = mutableStateMapOf(
            Pair(chordFamilies[0].id, false)
        )

    init {
        val notes = Notes().allNotes
        for (note in notes) {
            activeNotes[note] = MutableStateFlow(Pair(note, false))
        }
    }

    // Dont send signal to all FretStringViews, when chord changes; just affected ones
    fun updateCurrentChord(chord: Chord) {
        val oldChord = currentChord.value
        oldChord.notes.forEach {
            activeNotes[it]?.value = Pair(it, false)
        }
        chord.notes.forEach {
            activeNotes[it]?.value = Pair(it, true)
        }
        currentChord.value = chord
    }

    fun lastChord() {
        activated251Key.value?.let {
            current251Index--
            if (current251Index >= chords.size) current251Index = 0
            if (current251Index > -1 && current251Index < chords.size) {
                updateCurrentChord(it.chords[current251Index])
            }
            if (current251Index < 0) {
                current251Index = chords.size -1
                updateCurrentChord(it.chords[current251Index])
            }
            updateNextChord(it)
        } ?: run {
            chords.let {
                val currentChordIndex = rand(0, it.size - 1)
                if (currentChordIndex > -1) {
                    updateCurrentChord(it[currentChordIndex])
                }
            }
        }
    }

    fun nextChord() {
        activated251Key.value?.let {
            current251Index++
            if (current251Index >= chords.size) current251Index = 0
            if (current251Index > -1 && current251Index < chords.size) {
                updateCurrentChord(it.chords[current251Index])
                updateNextChord(it)
            }
        } ?: run {
            chords.let {
                val currentChordIndex = rand(0, it.size - 1)
                if (currentChordIndex > -1) {
                    updateCurrentChord(it[currentChordIndex])
                }
            }
        }
    }

    fun updateNextChord(key: Key) {
        var nextChordIndex = current251Index + 1
        if (nextChordIndex >= chords.size) nextChordIndex = 0
        if (nextChordIndex > -1 && nextChordIndex < chords.size) {
            nextChord.value = key.chords[nextChordIndex]
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
        }
    }

    fun rewind() {
        activated251Key.value?.let {
            current251Index = -1
            updateCurrentChord(Chord("","", listOf(), ""))
            resetTimer()
        }
    }

    var passedSixteenths: Long = 0
    var quartersPerMeasure: Long = 4
    var measureTime: Long = quartersPerMeasure * 4
    var timeSlotIndex = 0
    var timeSlots = mutableListOf<Long>(measureTime, measureTime*2, measureTime*3)

    fun handleQuarterSecond(switchChords: Boolean = true) {
        showCurrentChord.value = switchChords
        passedSixteenths++
        eightNoteBeatIndex.value = (passedSixteenths / 2) % 8

        var nextSlot: Long = 0
        if (timeSlotIndex < timeSlots.size) {
            nextSlot = timeSlots[timeSlotIndex]
        }
        if ((passedSixteenths) >= nextSlot) {
            slotPassed(switchChords)
            timeSlotIndex++
            if (timeSlotIndex >= timeSlots.size) {
                timeSlotIndex = 0
                passedSixteenths = 0
            }
        }
    }

    private fun slotPassed(switchChords: Boolean) {
        if (switchChords) {
            nextChord()
        }
    }

    fun resetTimer() {
        passedSixteenths = 0
        eightNoteBeatIndex.value = -1
        timeSlotIndex = timeSlots.size
    }

    fun setBpm(bpmValue: Long) {
        // bpm is just speed, handle in acticity for now
        if (bpmValue > 0) {
            bpm.value = bpmValue
            quarterNoteDuration.value = ((60f / bpmValue) * 1000).toLong() * 2
        } else {
            bpm.value = 0
            quarterNoteDuration.value = 0
        }
    }

    fun stepBack() {
        lastChord()
    }

    fun stepForward() {
        nextChord()
    }

    fun updateBeatOnly() {
        handleQuarterSecond(false)
    }
}