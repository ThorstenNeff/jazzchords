package com.neffapps.jazzchords.notes

data class ChordType (val id: String, val title: String, val chords: List<Chord>)

class ChordTypes {
    companion object {

        val dom7 = ChordType("dom7", "Dominant7th",
            Dominant7Chords().getDominant7Chords())

        val maj7 = ChordType("maj7", "Major7th",
            Major7Chords().getMajor7Chords())

        val min7 = ChordType("m7", "Minor7th",
            Minor7Chords().getMinor7Chords())

        val halfDim7 = ChordType("m7b5", "Half dimmed 7th",
            HalfDim7Chords().getMinor7B5Chords())

        val dim7 = ChordType("dim7", "Dimmed 7th",
            Dim7Chords().getDim7Chords())

        val allFamilies = listOf<ChordType>(
            dom7,
            maj7,
            min7,
            halfDim7,
            dim7
        )
    }
}