package com.neffapps.jazzchords.notes

data class Chord (
    val name: String,
    val flatName: String = "",
    val notes: List<Note>,
    val shape: String,
    val halfNoteType: HalfNoteType = HalfNoteType.SHARP,
)