package com.charlesmuchene.xr.ui.components

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.xr.compose.spatial.ContentEdge
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterOffsetType
import androidx.xr.compose.subspace.layout.SpatialRoundedCornerShape
import com.charlesmuchene.xr.R

@Composable
fun EnvSelectionOrbiter(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Orbiter(
        position = ContentEdge.Top,
        alignment = Alignment.Start,
        offset = 16.dp,
        offsetType = OrbiterOffsetType.InnerEdge,
        shape = SpatialRoundedCornerShape(CornerSize(100))
    ) {
        FilledTonalIconButton(modifier = modifier, onClick = onClick) {
            Icon(
                painter = painterResource(R.drawable.baseline_landscape_24),
                contentDescription = "content-description"
            )
        }
    }
}