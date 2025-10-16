package com.charlesmuchene.xr.ui.content

import androidx.compose.foundation.shape.CornerSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.ContentEdge
import androidx.xr.compose.spatial.Orbiter
import androidx.xr.compose.spatial.OrbiterOffsetType
import androidx.xr.compose.spatial.Subspace
import androidx.xr.compose.subspace.layout.SpatialRoundedCornerShape
import androidx.xr.scenecore.scene
import com.charlesmuchene.xr.env.ENV_OPTIONS
import com.charlesmuchene.xr.ui.components.HomeSpaceModeIconButton
import com.charlesmuchene.xr.ui.components.Jetliner

@Composable
fun SpatialContent(onRequestHomeSpaceMode: () -> Unit) {
    val session = checkNotNull(LocalSession.current)
    Subspace {
        Jetliner()
        if (LocalSpatialCapabilities.current.isAppEnvironmentEnabled) {
            LaunchedEffect(Unit) {
                session.scene.spatialEnvironment.preferredSpatialEnvironment =
                    ENV_OPTIONS[1].toSpatialEnvPref(session)
            }
        }
        Orbiter(
            position = ContentEdge.Top,
            alignment = Alignment.Start,
            offset = 16.dp,
            offsetType = OrbiterOffsetType.InnerEdge,
            shape = SpatialRoundedCornerShape(CornerSize(100))
        ) {
            HomeSpaceModeIconButton(onClick = onRequestHomeSpaceMode)
        }
    }
}