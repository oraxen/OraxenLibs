plugins {
    id("com.boy0000.conventions.kotlin.jvm")
    id("com.boy0000.conventions.papermc")
    id("com.boy0000.conventions.publication")
    id("com.boy0000.conventions.testing")
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
