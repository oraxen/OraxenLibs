@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.boy0000.conventions.kotlin.jvm")
    id("com.boy0000.conventions.papermc")
    id("com.boy0000.conventions.publication")
    id("com.boy0000.conventions.testing")
    alias(libs.plugins.kotlinx.serialization)
}

dependencies {
    compileOnly(libs.kotlinx.serialization.json)
    compileOnly(libs.kotlinx.serialization.kaml)
    implementation(project(":oraxenlibs-logging"))

    testImplementation(libs.kotlinx.serialization.json)
    testImplementation(libs.kotlinx.serialization.kaml)
}
