package com.neffapps.jazzchords.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.neffapps.jazzchords.MainViewModel
import com.neffapps.jazzchords.notes.Progressions
import com.neffapps.jazzchords.ui.theme.Anthrazit

@Composable
fun ProgressionPicker(
    viewModel: MainViewModel,
    progressions: Progressions,
) {
    val selected = viewModel.activated251Key.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var selectedIndex by remember { mutableStateOf(viewModel.current251Index) }
    val items = progressions.getMostCommon251Keys()

    Box(modifier = Modifier
        .padding(20.dp)
        .width(300.dp)
        .wrapContentSize(Alignment.TopCenter)) {
        Text(selected.value?.name ?: "",modifier = Modifier
            .height(50.dp)
            .clickable(onClick = { expanded = true })
            .background(
                Anthrazit
            )
            , Color.Yellow.copy(alpha = 0.6f)
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    Anthrazit
                )
        ) {
            items.forEachIndexed { index, key ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    viewModel.toggle251Key(key)
                }) {
                    Text(
                        text = key.name,
                        color = if (selected.value?.name == key.name)
                            Color.Yellow.copy(alpha = 0.6f)
                        else Color.LightGray.copy(alpha = 0.3f)
                    )
                }
            }
        }
    }
}