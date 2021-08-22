package com.neffapps.jazzchords.notes

data class Note(
    val fret: Int,
    val string: Int,
    val name: String,
    val textVisible: Boolean = true,
    val pressed: Boolean = false,
) {
    fun noteInChord(chordNotes: List<Note>): Boolean {
        for (note in chordNotes) {
            if (note.fret == fret && note.string == string) {
                return true
            }
        }
        return false
    }
}