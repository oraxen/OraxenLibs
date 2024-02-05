package com.boy0000.oraxenlibs.serialization

import com.mineinabyss.idofront.util.toColor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import org.bukkit.Color

@JvmInline
@Serializable
@SerialName("Color")
private value class ColorSurrogate(val color: String) {
    init {
        val split = color.splitColor()
        when {
            color.startsWith("#") ->
                require(color.drop(1).let { it.length == 6 || it.length == 8 }) { "Color must be in the form of '#rrggbb' or '#aarrggbb" }
            color.startsWith("0x") ->
                require(color.drop(2).let { it.length == 6 || it.length == 8 }) { "Color must be in the form of '0xrrggbb' or '0xaarrggbb" }
            ',' in color -> {
                require(split.size == 3 || split.size == 4) { "Color must be in the form of 'r, g, b' or 'a, r, g, b" }
                require(split.all { it in 0..255 }) { "Color must be in the range of 0-255" }
            }
            else -> require(color.toIntOrNull(16) != null) { "Color must be an integer" }
        }
    }
}

object ColorSerializer : KSerializer<Color> {
    override val descriptor: SerialDescriptor = ColorSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Color) {
        val color = if (value.alpha == 255) value.asRGB() else value.asARGB()
        encoder.encodeSerializableValue(
            ColorSurrogate.serializer(),
            ColorSurrogate("#${Integer.toHexString(color).uppercase()}")
        )
    }

    override fun deserialize(decoder: Decoder): Color {
        return decoder.decodeSerializableValue(ColorSurrogate.serializer()).color.toColor()
    }
}

private fun String.splitColor(): List<Int> = this.replace(" ", "").split(",").mapNotNull { it.toIntOrNull() }
