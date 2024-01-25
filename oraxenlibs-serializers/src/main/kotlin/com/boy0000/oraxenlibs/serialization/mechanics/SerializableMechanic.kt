package com.boy0000.oraxenlibs.serialization.mechanics

import kotlinx.serialization.Serializable

@Serializable
abstract class SerializableMechanic(val itemID: String, val factory: SerializableMechanicFactory)