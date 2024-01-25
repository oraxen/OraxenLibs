package com.mineinabyss.idofront.serialization.mechanics

import kotlinx.serialization.Serializable

@Serializable
abstract class SerializableMechanicFactory(val mechanicID: String) {

    companion object {
        private val mechanicByItem = mutableMapOf<String, SerializableMechanic>()
    }

    abstract fun parse(): SerializableMechanic

    fun addToImplemented(mechanic: SerializableMechanic) {
        mechanicByItem[mechanic.factory.mechanicID] = mechanic
    }

    fun mechanic() = mechanicByItem[mechanicID]
    fun itemIDs() = mechanicByItem.values

    fun hasMechanic(itemID: String) = itemID in mechanicByItem.keys

}