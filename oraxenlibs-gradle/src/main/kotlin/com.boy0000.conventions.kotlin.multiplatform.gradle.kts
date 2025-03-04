import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
    maven("https://repo.oraxen.com/releases")
}

val libs = the<LibrariesForLibs>()
val jvmVersion: Int = libs.versions.jvm.get().toInt()

kotlin {
    jvmToolchain(jvmVersion)
}
