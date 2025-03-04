package com.boy0000.oraxenlibs.serialization

import org.bukkit.inventory.ItemStack

/**
 * Somewhat hacky service for Geary support.
 * If registered, allows serializing Geary prefab items.
 */
// We extend a Kotlin function literal since we share Kotlin across all our plugins, but not this interface (Idofront is shaded)
interface SerializablePrefabItemService : (ItemStack, String) -> Unit {
    override fun invoke(item: ItemStack, prefabName: String) = encodeFromPrefab(item, prefabName)

    fun encodeFromPrefab(item: ItemStack, prefabName: String)
}
