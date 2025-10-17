package com.charlesmuchene.xr.ui.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.spatial.Subspace
import androidx.xr.scenecore.scene
import com.charlesmuchene.xr.env.ENV_OPTIONS
import com.charlesmuchene.xr.ui.components.Jetliner

@Composable
fun SpatialContent() {
    Subspace {
        Jetliner()

        val session = checkNotNull(LocalSession.current)
        if (LocalSpatialCapabilities.current.isAppEnvironmentEnabled) {
            LaunchedEffect(Unit) {
                session.scene.spatialEnvironment.preferredSpatialEnvironment =
                    ENV_OPTIONS[1].toSpatialEnvPref(session)
            }
        }
    }
}
