package com.neffapps.jazzchords.notes

class Keys {
    val major7Chords = Major7Chords().getMajor7Chords()
    val minor7Chords = Minor7Chords().getMinor7Chords()
    val dominant7Chords = Dominant7Chords().getDominant7Chords()
    val halfDim7Chords = HalfDim7Chords().getMinor7B5Chords()

    val emptyChord = Chord(name = "Empty", flatName = "", listOf(), "")

    fun getMaj7Chord(name: String, index:Int = 0): Chord {
        val chord = getChordWithIndex(name, index, major7Chords)
        chord.halfNoteType = getHalfNoteType(name)
        return chord
    }

    fun getMin7Chord(name: String, index:Int = 0): Chord {
        val chord = getChordWithIndex(name, index, minor7Chords)
        chord.halfNoteType = getHalfNoteType(name)
        return chord
    }

    fun getDom7Chord(name: String, index:Int = 0): Chord {
        val chord = getChordWithIndex(name, index, dominant7Chords)
        chord.halfNoteType = getHalfNoteType(name)
        return chord
    }

    fun getHalfDim7Chord(name: String, index:Int = 0): Chord {
        val chord = getChordWithIndex(name, index, halfDim7Chords)
        chord.halfNoteType = getHalfNoteType(name)
        return chord
    }

    private fun getChordWithIndex(name: String, index: Int, chords: List<Chord>): Chord {
        var chord = emptyChord
        val list = mutableListOf<Chord>()
        for (c in chords) {
            if (c.name == name || c.flatName == name) {
                list.add(c)
            }
        }
        list.sortBy { it.notes.sumOf { it.fret } }

        if (index < list.size) {
            chord = list[index]
        }
        return chord
    }

    private fun getHalfNoteType(name: String): HalfNoteType {
        val plainName = name.replace("b5","")
        return if ("b" in plainName) {
            HalfNoteType.FLAT
        } else if ("#" in plainName) {
            HalfNoteType.SHARP
        } else {
            HalfNoteType.NONE
        }
    }

    val Cmaj7 = Key(
        name = "Cmaj7",
        chords = listOf(
            getMaj7Chord("Cmaj7"),
            getMin7Chord("Dm7"),
            getMin7Chord("Em7"),
            getMaj7Chord("Fmaj7"),
            getDom7Chord("G7"),
            getDom7Chord("Am7"),
            getHalfDim7Chord("Bm7b5"),
        ),
        halfNoteType = HalfNoteType.NONE
    )

    val AscendingCmaj7 = Key(
        name = "Cmaj7",
        chords = listOf(
            getMin7Chord("Dm7"),
            getDom7Chord("G7"),
            getMaj7Chord("Cmaj7"),

            getMin7Chord("Dm7", 1),
            getDom7Chord("G7", 1),
            getMaj7Chord("Cmaj7", 1),

            getMin7Chord("Dm7", 2),
            getDom7Chord("G7", 2),
            getMaj7Chord("Cmaj7", 2),
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
            getHalfDim7Chord("F#7b5"),
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
            getHalfDim7Chord("C#7b5"),
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
            getHalfDim7Chord("G#7b5"),
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
            getHalfDim7Chord("D#7b5"),
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
            getHalfDim7Chord("A#7b5"),
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
            getHalfDim7Chord("Em7b5"),
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
            getHalfDim7Chord("Am7b5"),
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
            getHalfDim7Chord("Dm7b5"),
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
            getHalfDim7Chord("Gm7b5"),
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
            getHalfDim7Chord("Cm7b5"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    fun getMajor7Keys() = listOf(
        Cmaj7, Gmaj7, Dmaj7, Amaj7, Emaj7, Bmaj7, Fmaj7, Bbmaj7, Ebmaj7, Abmaj7, Dbmaj7
    )

    fun getMostCommon251Keys() = listOf(
        Cmaj7, AscendingCmaj7, Dmaj7, Fmaj7, Gmaj7, Bbmaj7,
    )

}