package com.boy0000.oraxenlibs.serialization.recipes

import kotlinx.serialization.Serializable
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.Recipe

@Serializable
sealed class SerializableRecipeIngredients {
    abstract fun toRecipe(key: NamespacedKey, result: ItemStack, group: String = ""): Recipe
}