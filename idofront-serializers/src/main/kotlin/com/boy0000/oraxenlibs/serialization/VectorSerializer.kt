package com.boy0000.oraxenlibs.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bukkit.util.Vector
import org.joml.Vector3f

@Serializable
@SerialName("Vector")
private class VectorSurrogate(
    val x: Float,
    val y: Float,
    val z: Float,
)

object VectorSerializer : KSerializer<Vector> {
    override val descriptor: SerialDescriptor = VectorSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Vector) {
        encoder.encodeSerializableValue(VectorSurrogate.serializer(), VectorSurrogate(value.x.toFloat(), value.y.toFloat(), value.z.toFloat()))
    }

    override fun deserialize(decoder: Decoder): Vector {
        val surrogate = decoder.decodeSerializableValue(VectorSurrogate.serializer())
        return Vector(surrogate.x, surrogate.y, surrogate.z)
    }
}

object Vector3fSerializer : KSerializer<Vector3f> {
    override val descriptor: SerialDescriptor = VectorSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Vector3f) {
        encoder.encodeSerializableValue(VectorSurrogate.serializer(), VectorSurrogate(value.x, value.y, value.z))
    }

    override fun deserialize(decoder: Decoder): Vector3f {
        val surrogate = decoder.decodeSerializableValue(VectorSurrogate.serializer())
        return Vector3f(surrogate.x, surrogate.y, surrogate.z)
    }
}
