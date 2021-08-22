package com.neffapps.jazzchords.notes

import androidx.compose.ui.unit.Dp

data class Fret(
    val number: Int,
    val notes: List<Note>,
    val width: Dp
)