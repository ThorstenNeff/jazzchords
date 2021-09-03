package com.neffapps.jazzchords.notes

class Progressions {
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

    val Cmaj7_251 = Key(
        name = "Cmaj7",
        chords = listOf(
            getMin7Chord("Dm7"),
            getDom7Chord("G7"),
            getMaj7Chord("Cmaj7"),
        ),
        halfNoteType = HalfNoteType.NONE
    )

    val Ascending251Cmaj7 = Key(
        name = "Cmaj7 (Ascending)",
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

    val Gmaj7_251 = Key(
        name = "Gmaj7",
        chords = listOf(
            getMin7Chord("Am7"),
            getDom7Chord("D7"),
            getMaj7Chord("Gmaj7"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Dmaj7_251 = Key(
        name = "Dmaj7",
        chords = listOf(
            getMin7Chord("Em7"),
            getDom7Chord("A7"),
            getMaj7Chord("Dmaj7"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Amaj7_251 = Key(
        name = "Amaj7",
        chords = listOf(
            getMin7Chord("Bm7"),
            getDom7Chord("E7"),
            getMaj7Chord("Amaj7"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Emaj7_251 = Key(
        name = "Emaj7",
        chords = listOf(
            getMin7Chord("F#m7"),
            getDom7Chord("B7"),
            getMaj7Chord("Emaj7"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Bmaj7_251 = Key(
        name = "Bmaj7",
        chords = listOf(
            getMin7Chord("C#m7"),
            getDom7Chord("F#7"),
            getMaj7Chord("Bmaj7"),
        ),
        halfNoteType = HalfNoteType.SHARP
    )

    val Fmaj7_251 = Key(
        name = "Fmaj7",
        chords = listOf(
            getMin7Chord("Gm7"),
            getDom7Chord("C7"),
            getMaj7Chord("Fmaj7"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Bbmaj7_251 = Key(
        name = "Bbmaj7",
        chords = listOf(
            getMin7Chord("Cm7"),
            getDom7Chord("F7"),
            getMaj7Chord("Bbmaj7"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Ebmaj7_251 = Key(
        name = "Ebmaj7",
        chords = listOf(
            getMin7Chord("Fm7"),
            getDom7Chord("Bb7"),
            getMaj7Chord("Ebmaj7"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Abmaj7_251 = Key(
        name = "Abmaj7",
        chords = listOf(
            getMin7Chord("Bbm7"),
            getDom7Chord("Eb7"),
            getMaj7Chord("Abmaj7"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    val Dbmaj7_251 = Key(
        name = "Dbmaj7",
        chords = listOf(
            getMin7Chord("Ebm7"),
            getMaj7Chord("Dbmaj7"),
            getDom7Chord("Ab7"),
        ),
        halfNoteType = HalfNoteType.FLAT
    )

    fun getMostCommon251Keys() = listOf(
        Cmaj7_251, Ascending251Cmaj7, Dmaj7_251, Fmaj7_251, Gmaj7_251, Bbmaj7_251,
    )

    fun getFlyMeToTheMoonKey() = listOf(
        Key(
            name = "Fly me to the moon in CMaj7",
            chords = listOf(
                getMin7Chord("Am7",0),
                getMin7Chord("Dm7", 0),
                getDom7Chord("G7", 0),
                getMaj7Chord("Cmaj7", 0),
            ),
            halfNoteType = HalfNoteType.SHARP,
        )
    )
}