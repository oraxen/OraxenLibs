package com.mineinabyss.idofront.serialization.mechanics

import kotlinx.serialization.Serializable

@Serializable
abstract class SerializableMechanic(val itemID: String, val factory: SerializableMechanicFactory)