package com.boy0000.oraxenlibs.serialization

import com.boy0000.oraxenlibs.textcomponents.miniMsg
import com.boy0000.oraxenlibs.textcomponents.serialize
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.kyori.adventure.text.Component

class MiniMessageSerializer : KSerializer<Component> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("Component", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Component) {
        encoder.encodeString(value.serialize())
    }

    override fun deserialize(decoder: Decoder): Component {
        return decoder.decodeString().miniMsg()
    }
}
