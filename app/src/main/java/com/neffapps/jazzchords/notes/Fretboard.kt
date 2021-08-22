package com.neffapps.jazzchords.notes

class Fretboard {

    companion object {

        private const val baseWidth = 20.0f

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

        fun getFret(
            position: Int,
            gNote: String,
            cNote: String,
            eNote: String,
            aNote: String,
        ) = Fret(
            position,
            listOf(
                Note(position,1,aNote),
                Note(position,2,eNote),
                Note(position,3,cNote),
                Note(position,4,gNote),
            ),
            baseWidth * widthMultipliers[position]
        )

        val AllFrets = listOf(
            getFret(0, "G","C","E","A"),
            getFret(1, "G#","C#","F","Bb"),
            getFret(2, "A","D","F#","B"),
            getFret(3, "Bb","D#","G","C"),
            getFret(4, "B","E","G#","C#"),
            getFret(5, "C","F","A","D"),
            getFret(6, "C#","F#","Bb","D#"),
            getFret(7, "D","G","B","E"),
            getFret(8, "D#","G#","C","F"),
            getFret(9, "E","A","C#","F#"),
            getFret(10, "F","Bb","D","G"),
            getFret(11, "F#","B","D#","G#"),
            getFret(12, "G","C","E","A"),
            getFret(13, "G#","C#","F","Bb"),
            getFret(14, "A","D","F#","B"),
            getFret(15, "Bb","D#","G","C"),
            getFret(16, "B","E","G#","C#"),
            getFret(17, "C","F","A","D"),
            getFret(18, "C#","F#","Bb","D#"),
            getFret(19, "D","G","B","E"),
        )
    }
}