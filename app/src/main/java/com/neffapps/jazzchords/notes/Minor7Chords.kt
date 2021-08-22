package com.neffapps.jazzchords.notes

class Minor7Chords {

    private val notes = Notes()

    private fun calculateAShapeMinor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until 20) {
            val aNote = notes.find(i, 1)
            val eNote = notes.find(i, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = aNote.name + "m7", shape = "A"))
            }
        }
        return chordList
    }

    private fun calculateEShapeMinor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 2 until 20) {
            val j = i - 2
            val aNote = notes.find(i, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(i, 3)
            val gNote = notes.find(j, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = eNote.name + "m7", shape = "E"))
            }
        }
        return chordList
    }

    private fun calculateCShapeMinor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 3 until 20) {
            val j = i - 2 // 0
            val k = i - 1 // 1
            val aNote = notes.find(i, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(k, 3)
            val gNote = notes.find(k, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = cNote.name + "m7", shape = "C"))
            }
        }
        return chordList
    }

    private fun calculateGShapeMinor7Chords(): List<Chord> {
        val chordList = mutableListOf<Chord>()
        for (i in 0 until 20) {
            val j = i + 1
            val k = i + 2
            val aNote = notes.find(j, 1)
            val eNote = notes.find(j, 2)
            val cNote = notes.find(k, 3)
            val gNote = notes.find(i, 4)
            if (aNote != null && eNote != null && cNote != null && gNote != null) {
                val notes = listOf(aNote, eNote, cNote, gNote)
                chordList.add(Chord(notes = notes, name = gNote.name + "m7", shape = "G"))
            }
        }
        return chordList
    }

    fun getMinor7Chords(): List<Chord> {
        val minor7Chords = mutableListOf<Chord>()
        minor7Chords.addAll(calculateAShapeMinor7Chords())
        minor7Chords.addAll(calculateEShapeMinor7Chords())
        minor7Chords.addAll(calculateCShapeMinor7Chords())
        minor7Chords.addAll(calculateGShapeMinor7Chords())
        return minor7Chords
    }

}