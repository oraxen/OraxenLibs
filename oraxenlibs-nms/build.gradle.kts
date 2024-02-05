@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.boy0000.conventions.kotlin.jvm")
    id("com.boy0000.conventions.papermc")
    id("com.boy0000.conventions.nms")
    id("com.boy0000.conventions.publication")
    alias(libs.plugins.kotlinx.serialization)
}

run {}
