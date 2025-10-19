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
    val modelEntity = loadModelEntity()

    AnimateModelEntity(modelEntity)

    Volume(modifier = modifier) { parent ->
        modelEntity?.let { it.parent = parent }
    }
}

@Composable
private fun AnimateModelEntity(modelEntity: GltfModelEntity?) {
    val angle = animatedAngle()
    LaunchedEffect(angle) {
        modelEntity?.let { entity ->
            val pose = calculatePose(angle)
            entity.setPose(pose)
        }
    }
}

@Composable
private fun animatedAngle(): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "A320Transition")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 12_000, easing = LinearEasing)),
        label = "A320Angle"
    )
    return angle
}

@Composable
private fun loadModelEntity(): GltfModelEntity? {
    val session = checkNotNull(LocalSession.current)
    var modelEntity by remember { mutableStateOf<GltfModelEntity?>(null) }

    LaunchedEffect(Unit) {
        val model = GltfModel.create(session, Paths.get("models", "a320neo.glb"))
        modelEntity = GltfModelEntity.create(session, model).apply {
            addComponent(MovableComponent.createSystemMovable(session))
            setScale(0.2f)
        }
    }
    return modelEntity
}

private fun calculatePose(angle: Float): Pose {
    val radians = angle * (PI.toFloat() / 180f)

    // Elliptical path
    val radiusX = 14f
    val radiusZ = 12f
    val x = radiusX * cos(radians)
    val z = radiusZ * sin(radians)

    // Center the ellipse and add vertical hover
    val hover = sin(radians * 6) * 0.2f // Small, fast vertical oscillation
    val position = Vector3(x, 6f + hover, z - 15f)

    // Calculate forward direction and bank
    val forwardX = -radiusX * sin(radians)
    val forwardZ = radiusZ * cos(radians)
    val forward = Vector3(forwardX, 0f, forwardZ).toNormalized()

    // Bank into the turn, varying between 15 and 25 degrees based on the path's curvature
    val bankAngle = 20f + 5f * cos(radians * 2)
    val bank = Quaternion.fromAxisAngle(forward, bankAngle)
    val up = bank * Vector3.Up

    val orientation = Quaternion.fromLookTowards(forward, up)
    return Pose(translation = position, rotation = orientation)
}
