plugins {
    id("com.mineinabyss.conventions.kotlin.jvm")
    id("com.mineinabyss.conventions.papermc")
    id("com.mineinabyss.conventions.publication")
    id("com.mineinabyss.conventions.testing")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    compileOnly(libs.kotlinx.serialization.json)
    implementation(project(":oraxenlibs-util"))
    api(project(":oraxenlibs-di"))
    api(project(":oraxenlibs-commands"))
    api(project(":oraxenlibs-logging"))
    api(project(":oraxenlibs-config"))
}
