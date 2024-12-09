package com.neffapps.jazzchords.notes

class HalfDim7Chords {

    private val notes = Notes()
    private val maxFretNumber = 12

    private fun calculateCShapeMinor7B5Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 3 until maxFretNumber) {
            val j = i - 1
            val k = i - 2
            val aNote = notes.find(i, 1)
            val eNote = notes.find(k, 2)
            val cNote = notes.find(j, 3)
            val gNote = notes.find(k, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(
                    notes = notes,
                    name = cNote.name + "m7b5",
                    flatName = cNote.flatName + "m7b5",
                    shape = "C"
                ))
            }
        }
        return chordList
    }

    private fun calculateAShapeMinor7B5Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 2 until maxFretNumber) {
            val j = i - 1
            val aNote = notes.find(i, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(
                    notes = notes,
                    name = aNote.name + "m7b5",
                    flatName = aNote.flatName + "m7b5",
                    shape = "A"))
            }
        }
        return chordList
    }

    private fun calculateEShapeMinor7B5Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 2 until maxFretNumber) {
            val j = i - 1
            val k = i - 2
            val barredaNote = notes.find(k, 1)
            val barredcNote = notes.find(k, 3)
            val aNote = notes.find(j, 1)
            val eNote = notes.find(k, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(k, 4)
            if (
                aNote != null && eNote != null && cNote != null && gNote != null
                && barredaNote != null && barredcNote != null
            ) {
                val notes = listOf(barredaNote, aNote, eNote, barredcNote, cNote, gNote)
                chordList.add(Chord(
                    notes = notes,
                    name = eNote.name + "m7b5",
                    flatName = eNote.flatName + "m7b5",
                    shape = "E"))
            }
        }
        return chordList
    }

    private fun calculateGShapeMinor7B5Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 1 until maxFretNumber) {
            val j = i - 1
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
                chordList.add(Chord(
                    notes = notes,
                    name = gNote.name + "m7b5",
                    flatName = gNote.flatName + "m7b5",
                    shape = "G"))
            }
        }
        return chordList
    }

    fun getMinor7B5Chords(): List<Chord> {
        val minor7B5Chords = mutableListOf<Chord>()
        minor7B5Chords.addAll(calculateAShapeMinor7B5Chords())
        minor7B5Chords.addAll(calculateEShapeMinor7B5Chords())
        minor7B5Chords.addAll(calculateCShapeMinor7B5Chords())
        minor7B5Chords.addAll(calculateGShapeMinor7B5Chords())
        return minor7B5Chords
    }
}