package com.neffapps.jazzchords.notes

class Dominant7Chords {
    private val notes = Notes()

    val maxFretNumber = 12

    private fun calculateCShapeDominant7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until maxFretNumber) {
            val k = i +1
            val barredNote = notes.find(i, 1)
            val aNote = notes.find(k, 1)
            val eNote = notes.find(i, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null
                && barredNote != null
            ) {
                val notes = listOf(barredNote, aNote, eNote, cNote, gNote)
                chordList.add(Chord(
                    notes = notes,
                    name = cNote.name + "7",
                    flatName = cNote.flatName + "7",
                    shape = "C"
                ))
            }
        }
        return chordList
    }

    private fun calculateAShapeDominant7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until (maxFretNumber - 1)) {
            val k = i + 1
            val aNote = notes.find(i, 1)
            val eNote = notes.find(i, 2)
            val barredNote = notes.find(i, 3)
            val cNote = notes.find(k, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null
                && eNote != null && cNote != null && gNote != null && barredNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote, barredNote)
                chordList.add(Chord(
                    notes = notes,
                    name = aNote.name + "7",
                    flatName = aNote.flatName + "7",
                    shape = "A"))
            }
        }
        return chordList
    }

    private fun calculateGShapeDominant7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until (maxFretNumber -2)) {
            val j = i + 1
            val k = i + 2
            val aNote = notes.find(k, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(k, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(
                    notes = notes,
                    name = gNote.name + "7",
                    flatName = gNote.flatName + "7",
                    shape = "G"
                ))
            }
        }
        return chordList
    }

    private fun calculateEShapeDominant7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until (maxFretNumber - 2)) {
            val j = i + 1
            val k = i + 2
            val aNote = notes.find(k, 1)
            val eNote = notes.find(i, 2)
            val cNote = notes.find(k, 3)
            val gNote = notes.find(j, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(
                    notes = notes,
                    name = eNote.name + "7",
                    flatName = eNote.flatName + "7",
                    shape = "E"))
            }
        }
        return chordList
    }

    fun getDominant7Chords(): List<Chord> {
        val dominant7Chords = mutableListOf<Chord>()
        dominant7Chords.addAll(calculateCShapeDominant7Chords())
        dominant7Chords.addAll(calculateAShapeDominant7Chords())
        dominant7Chords.addAll(calculateGShapeDominant7Chords())
        dominant7Chords.addAll(calculateEShapeDominant7Chords())
        return dominant7Chords
    }
}