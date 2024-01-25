package com.boy0000.oraxenlibs.serialization.recipes

import com.boy0000.oraxenlibs.serialization.SerializableItemStack
import com.boy0000.oraxenlibs.util.toMCKey
import kotlinx.serialization.Serializable

@Serializable
class SerializableRecipe(
    val key: String,
    val ingredients: SerializableRecipeIngredients,
    val result: SerializableItemStack,
    val group: String = "",
) {
    fun toCraftingRecipe() =
        ingredients.toRecipe(key.toMCKey(), result.toItemStack(), group)
}