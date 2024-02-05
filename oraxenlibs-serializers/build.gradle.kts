@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.boy0000.conventions.kotlin.jvm")
    id("com.boy0000.conventions.papermc")
    id("com.boy0000.conventions.publication")
    id("com.boy0000.conventions.testing")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.kotlinx.serialization.kaml)
    compileOnly(libs.minecraft.plugin.mythic.dist)
    compileOnly(libs.minecraft.plugin.mythic.crucible)
    compileOnly(libs.minecraft.plugin.itemsadder)
    implementation(project(":oraxenlibs-util"))
    implementation(project(":oraxenlibs-logging"))
    implementation(project(":oraxenlibs-text-components"))
    implementation(project(":oraxenlibs-di"))

    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.serialization.kaml)
    testImplementation(libs.minecraft.mockbukkit)
}
