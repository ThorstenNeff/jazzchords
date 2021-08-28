package com.neffapps.jazzchords.notes

enum class HalfNoteType {
    NONE,
    FLAT,
    SHARP,
}

data class Key (
    val name: String,
    val chords: List<Chord>,
    val halfNoteType: HalfNoteType = HalfNoteType.SHARP,
)