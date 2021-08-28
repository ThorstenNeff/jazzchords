package com.neffapps.jazzchords.notes

class Keys {
    val major7Chords = Major7Chords().getMajor7Chords()
    val minor7Chords = Minor7Chords().getMinor7Chords()
    val dominant7Chords = Dominant7Chords().getDominant7Chords()
    val halfDim7Chords = HalfDim7Chords().getMinor7B5Chords()

    val emptyChord = Chord(name = "Empty", flatName = "", listOf(), "")

    fun getMaj7Chord(name: String) =
        major7Chords.find { it.name == name || it.flatName == name } ?: emptyChord

    fun getMin7Chord(name: String) =
        minor7Chords.find { it.name == name || it.flatName == name  } ?: emptyChord

    fun getDom7Chord(name: String) =
        dominant7Chords.find { it.name == name || it.flatName == name  } ?: emptyChord

    fun getHalfDim7Chords(name: String) =
        halfDim7Chords.find { it.name == name || it.flatName == name  } ?: emptyChord

    val Cmaj7 = Key(
        name = "Cmaj7",
        chords = listOf(
            getMaj7Chord("Cmaj7"),
            getMin7Chord("Dm7"),
            getMin7Chord("Em7"),
            getMaj7Chord("Fmaj7"),
            getDom7Chord("G7"),
            getDom7Chord("Am7"),
            getHalfDim7Chords("Bm7b5"),
        ),
        halfNoteType = HalfNoteType.NONE
    )

    val Gmaj7 = Key(
        name = "Gmaj7",
        chords = listOf(
            getMaj7Chord("Gmaj7"),
            getMin7Chord("Am7"),
            getMin7Chord("Bm7"),
            getMaj7Chord("Cmaj7"),
            getDom7Chord("D7"),
            getDom7Chord("Em7"),
            getHalfDim7Chords("F#7b5"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Dmaj7 = Key(
        name = "Dmaj7",
        chords = listOf(
            getMaj7Chord("Dmaj7"),
            getMin7Chord("Em7"),
            getMin7Chord("F#m7"),
            getMaj7Chord("Gmaj7"),
            getDom7Chord("A7"),
            getDom7Chord("Bm7"),
            getHalfDim7Chords("C#7b5"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Amaj7 = Key(
        name = "Amaj7",
        chords = listOf(
            getMaj7Chord("Amaj7"),
            getMin7Chord("Bm7"),
            getMin7Chord("C#m7"),
            getMaj7Chord("Dmaj7"),
            getDom7Chord("E7"),
            getDom7Chord("F#m7"),
            getHalfDim7Chords("G#7b5"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Emaj7 = Key(
        name = "Emaj7",
        chords = listOf(
            getMaj7Chord("Emaj7"),
            getMin7Chord("F#m7"),
            getMin7Chord("G#m7"),
            getMaj7Chord("Amaj7"),
            getDom7Chord("B7"),
            getDom7Chord("C#m7"),
            getHalfDim7Chords("D#7b5"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Bmaj7 = Key(
        name = "Bmaj7",
        chords = listOf(
            getMaj7Chord("Bmaj7"),
            getMin7Chord("C#m7"),
            getMin7Chord("D#m7"),
            getMaj7Chord("Emaj7"),
            getDom7Chord("F#7"),
            getDom7Chord("G#m7"),
            getHalfDim7Chords("A#7b5"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Fmaj7 = Key(
        name = "Fmaj7",
        chords = listOf(
            getMaj7Chord("Fmaj7"),
            getMin7Chord("Gm7"),
            getMin7Chord("Am7"),
            getMaj7Chord("Bbmaj7"),
            getDom7Chord("C7"),
            getDom7Chord("Dm7"),
            getHalfDim7Chords("Em7b5"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Bbmaj7 = Key(
        name = "Bbmaj7",
        chords = listOf(
            getMaj7Chord("Bbmaj7"),
            getMin7Chord("Cm7"),
            getMin7Chord("Dm7"),
            getMaj7Chord("Ebmaj7"),
            getDom7Chord("F7"),
            getDom7Chord("Gm7"),
            getHalfDim7Chords("Am7b5"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Ebmaj7 = Key(
        name = "Ebmaj7",
        chords = listOf(
            getMaj7Chord("Ebmaj7"),
            getMin7Chord("Fm7"),
            getMin7Chord("Gm7"),
            getMaj7Chord("Abmaj7"),
            getDom7Chord("Bb7"),
            getDom7Chord("Cm7"),
            getHalfDim7Chords("Dm7b5"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Abmaj7 = Key(
        name = "Abmaj7",
        chords = listOf(
            getMaj7Chord("Abmaj7"),
            getMin7Chord("Bbm7"),
            getMin7Chord("Cm7"),
            getMaj7Chord("Dbmaj7"),
            getDom7Chord("Eb7"),
            getDom7Chord("Fm7"),
            getHalfDim7Chords("Gm7b5"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Dbmaj7 = Key(
        name = "Dbmaj7",
        chords = listOf(
            getMaj7Chord("Dbmaj7"),
            getMin7Chord("Ebm7"),
            getMin7Chord("Fm7"),
            getMaj7Chord("Gbmaj7"),
            getDom7Chord("Ab7"),
            getDom7Chord("Bm7"),
            getHalfDim7Chords("Cm7b5"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )
}