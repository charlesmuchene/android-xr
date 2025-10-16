package com.charlesmuchene.xr.ui

import androidx.compose.runtime.Composable
import androidx.xr.compose.platform.LocalSpatialCapabilities
import androidx.xr.compose.platform.LocalSpatialConfiguration
import com.charlesmuchene.xr.ui.content.SpatialContent
import com.charlesmuchene.xr.ui.content.The2DContent

@Composable
fun MainScreen() {
    val spatialConfiguration = LocalSpatialConfiguration.current

    if (LocalSpatialCapabilities.current.isSpatialUiEnabled) SpatialContent()
    else The2DContent(onRequestFullSpaceMode = spatialConfiguration::requestFullSpaceMode)
}


