@file:OptIn(InternalResourceApi::class)

package gvosy.composeapp.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/gvosy.composeapp.generated.resources/"

internal val Res.drawable.avatar_dove: DrawableResource by lazy {
      DrawableResource("drawable:avatar_dove", setOf(
        ResourceItem(setOf(), "${MD}drawable/avatar_dove.png", -1, -1),
      ))
    }

internal val Res.drawable.compose_multiplatform: DrawableResource by lazy {
      DrawableResource("drawable:compose_multiplatform", setOf(
        ResourceItem(setOf(), "${MD}drawable/compose-multiplatform.xml", -1, -1),
      ))
    }

internal val Res.drawable.icon_add: DrawableResource by lazy {
      DrawableResource("drawable:icon_add", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_add.png", -1, -1),
      ))
    }

internal val Res.drawable.icon_arrow: DrawableResource by lazy {
      DrawableResource("drawable:icon_arrow", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_arrow.png", -1, -1),
      ))
    }

internal val Res.drawable.icon_arrow1: DrawableResource by lazy {
      DrawableResource("drawable:icon_arrow1", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_arrow1.png", -1, -1),
      ))
    }

internal val Res.drawable.icon_arrow_drawable: DrawableResource by lazy {
      DrawableResource("drawable:icon_arrow_drawable", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_arrow_drawable.xml", -1, -1),
      ))
    }

internal val Res.drawable.icon_camera: DrawableResource by lazy {
      DrawableResource("drawable:icon_camera", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_camera.png", -1, -1),
      ))
    }

internal val Res.drawable.icon_mic: DrawableResource by lazy {
      DrawableResource("drawable:icon_mic", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_mic.png", -1, -1),
      ))
    }

internal val Res.drawable.icon_notes: DrawableResource by lazy {
      DrawableResource("drawable:icon_notes", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_notes.png", -1, -1),
      ))
    }

internal val Res.drawable.icon_settings: DrawableResource by lazy {
      DrawableResource("drawable:icon_settings", setOf(
        ResourceItem(setOf(), "${MD}drawable/icon_settings.png", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("avatar_dove", Res.drawable.avatar_dove)
  map.put("compose_multiplatform", Res.drawable.compose_multiplatform)
  map.put("icon_add", Res.drawable.icon_add)
  map.put("icon_arrow", Res.drawable.icon_arrow)
  map.put("icon_arrow1", Res.drawable.icon_arrow1)
  map.put("icon_arrow_drawable", Res.drawable.icon_arrow_drawable)
  map.put("icon_camera", Res.drawable.icon_camera)
  map.put("icon_mic", Res.drawable.icon_mic)
  map.put("icon_notes", Res.drawable.icon_notes)
  map.put("icon_settings", Res.drawable.icon_settings)
}
