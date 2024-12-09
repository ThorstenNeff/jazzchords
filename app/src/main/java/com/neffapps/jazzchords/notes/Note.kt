package com.neffapps.jazzchords.notes

data class Note(
    val fret: Int,
    val string: Int,
    val name: String,
    val flatName: String = "",
    val textVisible: Boolean = true,
    val pressed: Boolean = false,
) {
    fun noteInChord(chordNotes: List<Note>) = chordNotes.any { note ->
        note.fret == fret && note.string == string
    }
}