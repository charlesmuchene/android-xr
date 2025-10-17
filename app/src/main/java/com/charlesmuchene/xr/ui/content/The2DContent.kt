package com.charlesmuchene.xr.ui.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.xr.compose.platform.LocalSpatialConfiguration
import com.charlesmuchene.xr.R
import com.charlesmuchene.xr.ui.components.JetlinerTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun The2DContent() {
    val spatialConfiguration = LocalSpatialConfiguration.current
    val onRequestFullSpaceMode = { spatialConfiguration.requestFullSpaceMode() }

    Scaffold(topBar = { JetlinerTopAppBar(onClick = onRequestFullSpaceMode) }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MainContent(
                onRequestFullSpaceMode = onRequestFullSpaceMode,
                modifier = Modifier
                    .padding(48.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun MainContent(modifier: Modifier = Modifier, onRequestFullSpaceMode: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.jetliner),
        contentDescription = stringResource(R.string.jetliner),
        modifier = modifier
            .fillMaxSize()
            .clickable(onClick = onRequestFullSpaceMode)
    )
}
