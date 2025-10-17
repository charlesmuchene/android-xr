package com.charlesmuchene.xr.ui.content

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.xr.compose.platform.LocalSession
import com.charlesmuchene.xr.R
import com.charlesmuchene.xr.ui.components.FullSpaceModeIconButton

@Composable
fun The2DContent(onRequestFullSpaceMode: () -> Unit) {
    Surface {
        Box(modifier = Modifier.fillMaxSize()) {
            MainContent(
                onRequestFullSpaceMode = onRequestFullSpaceMode,
                modifier = Modifier
                    .padding(48.dp)
                    .align(Alignment.Center)
            )
            if (!LocalInspectionMode.current && LocalSession.current != null) {
                FullSpaceModeIconButton(
                    onClick = onRequestFullSpaceMode,
                    modifier = Modifier
                        .padding(32.dp)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}

@Composable
private fun MainContent(modifier: Modifier = Modifier, onRequestFullSpaceMode: () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Image(
            painter = painterResource(R.drawable.jetliner),
            contentDescription = stringResource(R.string.jetliner),
            modifier = Modifier
                .fillMaxWidth()
                .weight(.9f)
        )

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
            onClick = onRequestFullSpaceMode,
            modifier = Modifier
                .fillMaxWidth()
                .scale(animatedScale)
        ) {
            Text(stringResource(R.string.main_button), fontSize = 32.sp)
        }
    }
}
