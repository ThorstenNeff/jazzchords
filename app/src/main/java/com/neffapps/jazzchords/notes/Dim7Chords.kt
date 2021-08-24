package com.neffapps.jazzchords.notes

class Dim7Chords {

    private val notes = Notes()
    val maxFretNumber = 12

    private fun calculateCShapeDim7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 1 until maxFretNumber) {
            val j = i - 1
            val aNote = notes.find(i, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(j, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = cNote.name + "dim7", shape = "C"))
            }
        }
        return chordList
    }

    private fun calculateAShapeDim7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 1 until maxFretNumber) {
            val j = i - 1
            val aNote = notes.find(i, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(j, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = aNote.name + "dim7", shape = "A"))
            }
        }
        return chordList
    }

    private fun calculateEShapeDim7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until maxFretNumber) {
            val j = i + 1
            val aNote = notes.find(j, 1)
            val eNote = notes.find(i, 2)
            val cNote = notes.find(j, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = eNote.name + "dim7", shape = "E"))
            }
        }
        return chordList
    }

    private fun calculateGShapeDim7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until maxFretNumber) {
            val j = i + 1
            val aNote = notes.find(j, 1)
            val eNote = notes.find(i, 2)
            val cNote = notes.find(j, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = gNote.name + "dim7", shape = "G"))
            }
        }
        return chordList
    }

    fun getDim7Chords(): List<Chord> {
        val Dim7Chords = mutableListOf<Chord>()
        Dim7Chords.addAll(calculateAShapeDim7Chords())
        Dim7Chords.addAll(calculateEShapeDim7Chords())
        Dim7Chords.addAll(calculateCShapeDim7Chords())
        Dim7Chords.addAll(calculateGShapeDim7Chords())
        return Dim7Chords
    }
}