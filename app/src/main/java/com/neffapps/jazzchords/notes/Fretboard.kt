package com.neffapps.jazzchords.notes

object Fretboard {
    // private const val baseWidth = 20.0f

    private val allNotes = Notes()
    private val emptyNote = Note(0,0,"Empty")

    private val widthMultipliers = listOf<Float>(
        1.4033f,
        1.32454f,
        1.2502f,
        1.18003f,
        1.1138f,
        1.05129f,
        0.99228f,
        0.93659f,
        0.88402f,
        0.83441f,
        0.78758f,
        0.74337f,
        0.70165f,
        0.66227f,
        0.6251f,
        0.59001f,
        0.5569f,
        0.52564f,
        0.49614f,
        0.4683f,
        0.44201f,
        0.4172f,
        0.39379f,
        0.37169f,
    )

    private fun getFret(
        position: Int,
        baseWidth: Float = 20.0f,
    ) = Fret(
        position,
        listOf(
            allNotes.find(position, 1) ?: emptyNote,
            allNotes.find(position, 2) ?: emptyNote,
            allNotes.find(position, 3) ?: emptyNote,
            allNotes.find(position, 4) ?: emptyNote,
        ),
        baseWidth * widthMultipliers[position]
    )

    fun getAllFrets(baseWidth: Float): List<Fret> = listOf(
        getFret(0, baseWidth),
        getFret(1, baseWidth),
        getFret(2, baseWidth),
        getFret(3, baseWidth),
        getFret(4, baseWidth),
        getFret(5, baseWidth),
        getFret(6, baseWidth),
        getFret(7, baseWidth),
        getFret(8, baseWidth),
        getFret(9, baseWidth),
        getFret(10, baseWidth),
        getFret(11, baseWidth),
        getFret(12, baseWidth),
        getFret(13, baseWidth),
        getFret(14, baseWidth),
        getFret(15, baseWidth),
        getFret(16, baseWidth),
        getFret(17, baseWidth),
        getFret(18, baseWidth),
        getFret(19, baseWidth),
    )
}