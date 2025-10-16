package com.charlesmuchene.xr.ui.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.xr.compose.platform.LocalSession
import androidx.xr.compose.subspace.ExperimentalSubspaceVolumeApi
import androidx.xr.compose.subspace.Volume
import androidx.xr.compose.subspace.layout.SubspaceModifier
import androidx.xr.runtime.math.Pose
import androidx.xr.runtime.math.Quaternion
import androidx.xr.runtime.math.Vector3
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.GltfModelEntity
import androidx.xr.scenecore.MovableComponent
import java.nio.file.Paths
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalSubspaceVolumeApi::class)
@Composable
fun Jetliner(modifier: SubspaceModifier = SubspaceModifier) {
    val session = checkNotNull(LocalSession.current)

    // --- Animation Setup ---
    val infiniteTransition = rememberInfiniteTransition(label = "A320Transition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 12_000, easing = LinearEasing)),
        label = "A320Angle"
    )

    // --- Model and Entity State ---
    var modelEntity by remember { mutableStateOf<GltfModelEntity?>(null) }

    // --- Model Loading and Entity Creation (runs only once) ---
    LaunchedEffect(Unit) {
        val model = GltfModel.create(session, Paths.get("models", "a320neo.glb"))
        modelEntity = GltfModelEntity.create(session, model).apply {
            addComponent(MovableComponent.createSystemMovable(session))
            setScale(0.2f)
        }
    }

    // --- Pose Updates on Animation Change ---
    LaunchedEffect(angle) {
        modelEntity?.let { entity ->
            val radians = angle * (PI.toFloat() / 180f)

            // Elliptical path
            val radiusX = 12f
            val radiusZ = 16f
            val x = radiusX * cos(radians)
            val z = radiusZ * sin(radians)

            // Center the ellipse and add vertical hover
            val hover = sin(radians * 6) * 0.2f // Small, fast vertical oscillation
            val position = Vector3(x, 2f + hover, z - 20f)

            // Calculate forward direction and bank
            val forwardX = -radiusX * sin(radians)
            val forwardZ = radiusZ * cos(radians)
            val forward = Vector3(forwardX, 0f, forwardZ).toNormalized()

            // Bank based on the curvature of the path
            val bankAngle = -cos(radians * 2) * 15f // Bank into turns
            val bank = Quaternion.fromAxisAngle(forward, bankAngle)
            val up = bank * Vector3.Up

            val orientation = Quaternion.fromLookTowards(forward, up)
            val pose = Pose(position, orientation)
            entity.setPose(pose)
        }
    }

    // --- Scene Graph Placement ---
    Volume(modifier = modifier) { parent ->
        modelEntity?.let { it.parent = parent }
    }
}
