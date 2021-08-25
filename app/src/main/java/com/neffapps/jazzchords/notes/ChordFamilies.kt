package com.neffapps.jazzchords.notes

data class ChordFamily (val id: String, val title: String, val chords: List<Chord>)

class ChordFamilies {
    companion object {

        val dom7 = ChordFamily("dom7", "Dominant7th",
            Dominant7Chords().getDominant7Chords())

        val maj7 = ChordFamily("maj7", "Major7th",
            Major7Chords().getMajor7Chords())

        val min7 = ChordFamily("m7", "Minor7th",
            Minor7Chords().getMinor7Chords())

        val halfDim7 = ChordFamily("m7b5", "Half dimmed 7th",
            HalfDim7Chords().getMinor7B5Chords())

        val dim7 = ChordFamily("dim7", "Dimmed 7th",
            Dim7Chords().getDim7Chords())

        val allFamilies = listOf<ChordFamily>(
            dom7,
            maj7,
            min7,
            halfDim7,
            dim7
        )
    }
}