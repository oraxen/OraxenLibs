plugins {
    id("com.boy0000.conventions.kotlin.jvm")
    id("com.boy0000.conventions.copyjar")
    id("com.boy0000.conventions.papermc")
    id("com.boy0000.conventions.nms")
}

dependencies {
    val libs = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")
    libs.findBundle("platform").get().get().forEach {
        implementation(it)
    }

    rootProject.subprojects
        .filter { it.name.startsWith("oraxenlibs-") }
        .filter { it.name !in setOf("oraxenlibs-catalog", "oraxenlibs-catalog-shaded") }
        .forEach {implementation(project(it.path)) }
}

copyJar {
    jarName.set("OraxenLibs-$version.jar")
    excludePlatformDependencies.set(false)
}
