package com.boy0000.oraxenlibs.serialization.recipes

import com.boy0000.oraxenlibs.items.editItemMeta
import com.boy0000.oraxenlibs.serialization.SerializableItemStack
import com.boy0000.oraxenlibs.serialization.toSerializable
import io.papermc.paper.potion.PotionMix
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.PotionType

@Serializable
@SerialName("potionmix")
class PotionMixRecipeIngredients(
    private val input: SerializableItemStack =
        ItemStack(Material.POTION).editItemMeta<PotionMeta> { basePotionType = PotionType.WATER }.toSerializable(),
    private val ingredient: SerializableItemStack,
) {
    fun toPotionMix(key: NamespacedKey, result: ItemStack): PotionMix {
        return PotionMix(
            key,
            result,
            PotionMix.createPredicateChoice(input::matches),
            PotionMix.createPredicateChoice(ingredient::matches)
        )
    }
}
