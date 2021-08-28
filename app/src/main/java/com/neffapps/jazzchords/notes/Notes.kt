package com.neffapps.jazzchords.notes

class Notes {
    fun find(fret: Int, string: Int): Note? {
        for (note in allNotes) {
            if (note.fret == fret && note.string == string) {
                return note
            }
        }
        return null
    }

    private val allNotes: List<Note> = listOf(
        G_0_G,
        GSharp_1_G,
        A_2_G,
        BFlat_3_G,

        B_4_G,
        C_5_G,
        CSharp_6_G,
        D_7_G,

        DSharp_8_G,
        E_9_G,
        F_10_G,
        FSharp_11_G,

        G_12_G,
        GSharp_13_G,
        A_14_G,
        BFlat_15_G,

        B_16_G,
        C_17_G,
        CSharp_18_G,
        D_19_G,

        // C String (3rd string)
        C_0_C,
        CSharp_1_C,
        D_2_C,
        DSharp_3_C,

        E_4_C,
        F_5_C,
        FSharp_6_C,
        G_7_C,

        GSharp_8_C,
        A_9_C,
        BFlat_10_C,
        B_11_C,

        C_12_C,
        CSharp_13_C,
        D_14_C,
        DSharp_15_C,

        E_16_C,
        F_17_C,
        FSharp_18_C,
        G_19_C,

        // E String (2nd string)
        E_0_E,
        F_1_E,
        FSharp_2_E,
        G_3_E,

        GSharp_4_E,
        A_5_E,
        BFlat_6_E,
        B_7_E,

        C_8_E,
        CSharp_9_E,
        D_10_E,
        DSharp_11_E,

        E_12_E,
        F_13_E,
        FSharp_14_E,
        G_15_E,

        GSharp_16_E,
        A_17_E,
        BFlat_18_E,
        B_19_E,

        // A String (1st string)
        A_0_A,
        BFlat_1_A,
        B_2_A,
        C_3_A,

        CSharp_4_A,
        D_5_A,
        DSharp_6_A,
        E_7_A,

        F_8_A,
        FSharp_9_A,
        G_10_A,
        GSharp_11_A,

        A_12_A,
        BFlat_13_A,
        B_14_A,
        C_15_A,

        CSharp_16_A,
        D_17_A,
        DSharp_18_A,
        E_19_A,
    )

    companion object {
        // G String (4th String)
        val G_0_G = Note(0,4, "G")
        val GSharp_1_G = Note(1,4, "G#")
        val A_2_G = Note(2,4, "A")
        val BFlat_3_G = Note(3,4, "A#", "Bb")

        val B_4_G = Note(4,4, "B")
        val C_5_G = Note(5,4, "C")
        val CSharp_6_G = Note(6,4, "C#", "Db")
        val D_7_G = Note(7,4, "D")

        val DSharp_8_G = Note(8,4, "D#", "Eb")
        val E_9_G = Note(9,4, "E")
        val F_10_G = Note(10,4, "F")
        val FSharp_11_G = Note(11,4, "F#")

        val G_12_G = Note(12,4, "G")
        val GSharp_13_G = Note(13,4, "G#", "Eb")
        val A_14_G = Note(14,4, "A")
        val BFlat_15_G = Note(15,4, "A#", "Bb")

        val B_16_G = Note(16,4, "B")
        val C_17_G = Note(17,4, "C")
        val CSharp_18_G = Note(18,4, "C#", "Db")
        val D_19_G = Note(19,4, "D")

        // C String (3rd string)
        val C_0_C = Note(0,3, "C")
        val CSharp_1_C = Note(1,3, "C#", "Db")
        val D_2_C = Note(2,3, "D")
        val DSharp_3_C = Note(3, 3, "D#", "Eb")

        val E_4_C = Note(4,3, "E")
        val F_5_C = Note(5,3, "F")
        val FSharp_6_C = Note(6,3, "F#", "Gb")
        val G_7_C = Note(7, 3, "G")

        val GSharp_8_C = Note(8,3, "G#", "Eb")
        val A_9_C = Note(9,3, "A")
        val BFlat_10_C = Note(10,3, "A#", "Bb")
        val B_11_C = Note(11, 3, "B")

        val C_12_C = Note(12,3, "C")
        val CSharp_13_C = Note(13,3, "C#", "Db")
        val D_14_C = Note(14,3, "D")
        val DSharp_15_C = Note(15, 3, "D#", "Eb")

        val E_16_C = Note(16,3, "E")
        val F_17_C = Note(17,3, "F")
        val FSharp_18_C = Note(18,3, "F#", "Gb")
        val G_19_C = Note(19, 3, "G")

        // E String (2nd string)
        val E_0_E = Note(0,2, "E")
        val F_1_E = Note(1,2, "F")
        val FSharp_2_E = Note(2,2, "F#", "Gb")
        val G_3_E = Note(3, 2, "G")

        val GSharp_4_E = Note(4,2, "G#", "Eb")
        val A_5_E = Note(5,2, "A")
        val BFlat_6_E = Note(6,2, "A#", "Bb")
        val B_7_E = Note(7, 2, "B")

        val C_8_E = Note(8,2, "C")
        val CSharp_9_E = Note(9,2, "C#", "Db")
        val D_10_E = Note(10,2, "D")
        val DSharp_11_E = Note(11, 2, "D#", "Eb")

        val E_12_E = Note(12,2, "E")
        val F_13_E = Note(13,2, "F")
        val FSharp_14_E = Note(14,2, "F#", "Gb")
        val G_15_E = Note(15, 2, "G")

        val GSharp_16_E = Note(16,2, "G#", "Eb")
        val A_17_E = Note(17,2, "A")
        val BFlat_18_E = Note(18,2, "A#", "Bb")
        val B_19_E = Note(19, 2, "B")

        // A String (1st string)
        val A_0_A = Note(0,1, "A")
        val BFlat_1_A = Note(1,1, "A#", "Bb")
        val B_2_A = Note(1,1, "B")
        val C_3_A = Note(3, 1, "C")

        val CSharp_4_A = Note(4,1, "C#", "Db")
        val D_5_A = Note(5,1, "D")
        val DSharp_6_A = Note(6,1, "D#", "Eb")
        val E_7_A = Note(7, 1, "E")

        val F_8_A = Note(8,1, "F")
        val FSharp_9_A = Note(9,1, "F#", "Gb")
        val G_10_A = Note(10,1, "G")
        val GSharp_11_A = Note(11, 1, "G#", "Eb")

        val A_12_A = Note(11,1, "A")
        val BFlat_13_A = Note(13,1, "A#", "Bb")
        val B_14_A = Note(14,1, "B")
        val C_15_A = Note(15, 1, "C")

        val CSharp_16_A = Note(16,1, "C#", "Db")
        val D_17_A = Note(17,1, "D")
        val DSharp_18_A = Note(18,1, "D#", "Eb")
        val E_19_A = Note(19, 1, "E")
    }
}