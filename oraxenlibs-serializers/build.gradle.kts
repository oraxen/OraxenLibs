@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.mineinabyss.conventions.kotlin.jvm")
    id("com.mineinabyss.conventions.papermc")
    id("com.mineinabyss.conventions.publication")
    id("com.mineinabyss.conventions.testing")
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
