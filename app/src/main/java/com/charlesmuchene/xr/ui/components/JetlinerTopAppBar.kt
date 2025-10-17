package com.charlesmuchene.xr.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.compose.platform.LocalSpatialConfiguration
import androidx.xr.compose.spatial.ContentEdge
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterOffsetType
import androidx.xr.compose.subspace.layout.SpatialRoundedCornerShape
import com.charlesmuchene.xr.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetlinerTopAppBar(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TopAppBar(
        modifier = modifier,
        title = { Text(stringResource(R.string.app_name)) },
        actions = {
            if (LocalSpatialConfiguration.current.hasXrSpatialFeature) {
                Orbiter(
                    offset = 16.dp,
                    alignment = Alignment.End,
                    position = ContentEdge.Top,
                    offsetType = OrbiterOffsetType.InnerEdge,
                    shape = SpatialRoundedCornerShape(CornerSize(percent = 100)),
                ) {
                    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                    val animatedScale by infiniteTransition.animateFloat(
                        initialValue = 1.0f,
                        targetValue = 1.01f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(durationMillis = 1000, easing = LinearEasing),
                            repeatMode = RepeatMode.Reverse
                        ),
                        label = "pulse-animation"
                    )

                    Button(
                        onClick = onClick,
                        modifier = Modifier
                            .padding(16.dp)
                            .scale(animatedScale)
                    ) {
                        Text(stringResource(R.string.main_button), fontSize = 16.sp)
                    }
                }
            }
        }
    )
}