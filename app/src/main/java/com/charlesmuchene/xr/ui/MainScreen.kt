package com.charlesmuchene.xr.ui

import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSpatialCapabilities
import com.charlesmuchene.xr.ui.content.SpatialContent
import com.charlesmuchene.xr.ui.content.The2DContent

@Composable
fun MainScreen() {
    val spatialUiEnabled = LocalSpatialCapabilities.current.isSpatialUiEnabled

    if (spatialUiEnabled) SpatialContent() else The2DContent()
}


