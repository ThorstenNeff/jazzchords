package com.neffapps.jazzchords.notes

class Major7Chords {

    private val notes = Notes()
    val maxFretNumber = 12

    private fun calculateAShapeMajor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 1 until maxFretNumber) {
            val j = i - 1
            val aNote = notes.find(j, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = aNote.name + "maj7", shape = "A"))
            }
        }
        return chordList
    }

    private fun calculateEShapeMajor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 3 until maxFretNumber) {
            val j = i - 2
            val k = i - 1
            val l = i - 3
            val aNote = notes.find(k, 1)
            val eNote = notes.find(l, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(j, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = eNote.name + "maj7", shape = "E"))
            }
        }
        return chordList
    }

    private fun calculateCShapeMajor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 2 until maxFretNumber) {
            val j = i - 2 // 0
            val barredANote = notes.find(j, 1)
            val aNote = notes.find(i, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(j, 3)
            val gNote = notes.find(j, 4)
            if (barredANote != null
                && aNote != null && eNote != null && cNote != null && gNote != null
            ) {
                val notes = listOf(barredANote, aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = cNote.name + "maj7", shape = "C"))
            }
        }
        return chordList
    }

    private fun calculateGShapeMajor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 2 until maxFretNumber) {
            val j = i - 2
            val barredaNote = notes.find(j, 1)
            val barredeNote = notes.find(j, 2)
            val barredcNote = notes.find(j, 3)
            val aNote = notes.find(i, 1)
            val eNote = notes.find(i, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(j, 4)
            if (
                aNote != null && eNote != null && cNote != null && gNote != null
                && barredaNote != null && barredeNote != null && barredcNote != null
            ) {
                val notes = listOf(
                    barredaNote, aNote, barredeNote, eNote, barredcNote, cNote, gNote
                )
                chordList.add(Chord(notes = notes, name = gNote.name + "maj7", shape = "G"))
            }
        }
        return chordList
    }

    fun getMajor7Chords(): List<Chord> {
        val major7Chords = mutableListOf<Chord>()
        major7Chords.addAll(calculateAShapeMajor7Chords())
        major7Chords.addAll(calculateEShapeMajor7Chords())
        major7Chords.addAll(calculateCShapeMajor7Chords())
        major7Chords.addAll(calculateGShapeMajor7Chords())
        return major7Chords
    }
    
}