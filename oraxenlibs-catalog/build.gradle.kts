import org.jetbrains.kotlin.util.prefixIfNot

plugins {
    `version-catalog`
    id("com.boy0000.conventions.publication")
}

catalog {
    versionCatalog {
        from(rootProject.files("gradle/libs.versions.toml"))
        // Add aliases for all our conventions plugins
        rootProject.file("oraxenlibs-gradle/src/main/kotlin").list()?.forEach { name ->
            val id = name.removeSuffix(".gradle.kts")
            plugin(id.removePrefix("com.boy0000.conventions").prefixIfNot("boy"), id).version(version.toString())
        }
        // Add all oraxenlibs projects to the catalog
        rootProject.file(".").list()?.filter { it.startsWith("oraxenlibs") }?.forEach { name ->
            library(name, "com.boy0000:$name:$version")
        }
        bundle(
            "oraxenlibs-core", listOf(
                "oraxenlibs-commands",
                "oraxenlibs-config",
                "oraxenlibs-di",
                "oraxenlibs-features",
                "oraxenlibs-fonts",
                "oraxenlibs-logging",
                "oraxenlibs-serializers",
                "oraxenlibs-text-components",
                "oraxenlibs-util",
            )
        )
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["versionCatalog"])
            artifactId = "catalog"
        }
    }
}
