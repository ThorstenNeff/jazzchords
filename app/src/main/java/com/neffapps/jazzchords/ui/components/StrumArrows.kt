package com.neffapps.jazzchords.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import com.neffapps.jazzchords.MainViewModel
import com.neffapps.jazzchords.R
import com.neffapps.jazzchords.strums.StrumType
import com.neffapps.jazzchords.ui.theme.Anthrazit

@ExperimentalUnitApi
@Composable
fun StrumArrows(viewModel: MainViewModel, width: Double) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy((-4).dp),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(Dp(width.toFloat()))
        ) {
            StrumArrow(viewModel, 0, StrumType.DOWN)
            StrumArrow(viewModel, 1, StrumType.UP)
            Spacer(modifier = Modifier.width(15.dp))
            StrumArrow(viewModel, 2, StrumType.DOWN)
            StrumArrow(viewModel, 3, StrumType.UP)
            Spacer(modifier = Modifier.width(15.dp))
            StrumArrow(viewModel, 4, StrumType.DOWN)
            StrumArrow(viewModel, 5, StrumType.UP)
            Spacer(modifier = Modifier.width(15.dp))
            StrumArrow(viewModel, 6, StrumType.DOWN)
            StrumArrow(viewModel, 7, StrumType.UP)
        }
    }
}

@ExperimentalUnitApi
@Composable
fun StrumArrow(
    viewModel: MainViewModel,
    index: Int,
    type: StrumType
) {
    val beat = viewModel.eightNoteBeatIndex.collectAsState()

    val drawable = when (type) {
        StrumType.DOWN -> ImageVector.vectorResource(id = R.drawable.ic_uparrow)
        StrumType.UP -> ImageVector.vectorResource(id = R.drawable.ic_downarrow)
        StrumType.MISS -> ImageVector.vectorResource(id = R.drawable.ic_miss)
    }
    Icon(
        imageVector = drawable,
        contentDescription = "",
        modifier = Modifier.size(24.dp, 60.dp),
        tint = if (beat.value < 0) {
            Anthrazit
        } else if (beat.value >= index) {
            Color.LightGray
        } else {
            Color.Black
        }
    )
}
