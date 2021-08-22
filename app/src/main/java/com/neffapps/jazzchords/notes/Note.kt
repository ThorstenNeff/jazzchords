package com.neffapps.jazzchords.notes

data class Note(
    val fret: Int,
    val string: Int,
    val name: String,
    val textVisible: Boolean = true,
    val pressed: Boolean = false,
)