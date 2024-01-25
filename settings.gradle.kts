pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven("https://repo.mineinabyss.com/releases")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "OraxenLibs"

includeBuild("oraxenlibs-gradle")

val projects = listOf(
    "oraxenlibs-catalog",
    "oraxenlibs-catalog-shaded",
    "oraxenlibs-commands",
    "oraxenlibs-config",
    "oraxenlibs-di",
    "oraxenlibs-features",
    "oraxenlibs-fonts",
    "oraxenlibs-logging",
    "oraxenlibs-nms",
    "oraxenlibs-serializers",
    "oraxenlibs-text-components",
    "oraxenlibs-util",)

include(projects)
