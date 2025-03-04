package com.boy0000.oraxenlibs.config

import kotlinx.serialization.KSerializer
import java.nio.file.Path
import java.nio.file.attribute.FileAttribute
import kotlin.io.path.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class Config<T>(
    val name: String,
    val path: Path,
    val default: T,
    val serializer: KSerializer<T>,
    val preferredFormat: Format,
    val formats: ConfigFormats,
    val mergeUpdates: Boolean,
    val lazyLoad: Boolean,
    val onFirstLoad: (T) -> Unit = {},
    val onReload: (T) -> Unit = {},
) : ReadWriteProperty<Any?, T> {
    private var loaded: T? = null
    private val fileFormat = checkFormat()
    private val fileName = "$name.${fileFormat.ext}"
    private val configFile = (path / fileName).createParentDirectories()

    init {
        if (!lazyLoad) getOrLoad()
    }

    private fun checkFormat(): Format {
        return formats.formats.firstOrNull {
            val file = path / "$name.${it.ext}"
            file.exists()
        } ?: preferredFormat
    }

    fun getOrLoad(): T {
        loaded?.let { return it }
        return load().also(onFirstLoad)
    }

    fun reload(): T {
        return load().also(onReload)
    }

    private fun load(): T {
        val decoded = when {
            configFile.exists() && configFile.readText().isNotEmpty() -> {
                configFile.inputStream().use { stream ->
                    formats.decode(
                        preferredFormat.stringFormat,
                        serializer,
                        stream
                    )
                }
            }

            else -> {
                configFile.toFile().createNewFile()
                default
            }
        }

        // Merge with any new changes
        if (mergeUpdates) write(decoded)
        return decoded.also { loaded = it }
    }

    fun write(data: T) {
        configFile.outputStream().use { stream ->
            formats.encode(
                fileFormat.stringFormat,
                serializer,
                stream,
                data
            )
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T = getOrLoad()

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) = write(value)
}
