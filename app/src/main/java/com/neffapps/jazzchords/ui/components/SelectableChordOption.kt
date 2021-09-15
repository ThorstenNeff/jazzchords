package com.neffapps.jazzchords.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.neffapps.jazzchords.MainViewModel
import com.neffapps.jazzchords.notes.ChordType

@ExperimentalUnitApi
@Composable
fun SelectableChordOption(
    viewModel: MainViewModel,
    chordType: ChordType,
) {
    val selected = viewModel.activatedChordFamilies[chordType.id]

    Box(modifier = Modifier
        .padding(top = 15.dp, start = 30.dp, end = 80.dp)
        .clickable {
            viewModel.toggleFamily(chordType)
        }
    ) {
        Text(
            text = chordType.title,
            color = if (selected == true)
                Color.Yellow.copy(alpha = 0.6f)
            else Color.LightGray.copy(alpha = 0.3f)
        )
    }
}