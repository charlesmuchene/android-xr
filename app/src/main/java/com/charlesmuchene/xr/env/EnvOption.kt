package com.charlesmuchene.xr.env

import androidx.xr.runtime.Session
import androidx.xr.scenecore.ExrImage
import androidx.xr.scenecore.GltfModel
import androidx.xr.scenecore.SpatialEnvironment
import kotlin.io.path.Path

data class EnvOption(val name: String, val skyboxPath: String?, val geometryPath: String?) {
    suspend fun toSpatialEnvPref(session: Session): SpatialEnvironment.SpatialEnvironmentPreference? {
        if (skyboxPath == null && geometryPath == null) return null
        else {
            val skybox = skyboxPath?.let {
                ExrImage.createFromZip(session, Path(it))
            }

            val geometry = geometryPath?.let {
                GltfModel.create(session, Path(it))
            }

            return SpatialEnvironment.SpatialEnvironmentPreference(skybox, geometry)
        }
    }
}

val DEFAULT_ENV = EnvOption("Default", null, null)

val ENV_OPTIONS = listOf(
    DEFAULT_ENV,
    EnvOption(name = "Forest", skyboxPath = null, geometryPath = "models/green_hills.glb"),
)