import kotlin.jvm.optionals.getOrNull

plugins {
    java
    com.github.johnrengelman.shadow
}

val pluginPath = project.findProperty("oraxen2_plugin_path") as? String

interface CopyJarExtension {
    // A configurable greeting
    val destPath: Property<String>

    val jarName: Property<String>

    val excludePlatformDependencies: Property<Boolean>
}

val copyJar = project.extensions.create<CopyJarExtension>("copyJar")

if (pluginPath != null) {
    tasks {
        register<Copy>("copyJar") {
            doNotTrackState("Overwrites the plugin jar to allow for easier reloading")
            val dest = copyJar.destPath.orNull ?: pluginPath
            val jarName = copyJar.jarName.orNull ?: "${project.name}-${project.version}.jar"
            println("Copying to plugin directory $dest for ${copyJar.jarName.orNull ?: "null"}")

            from(findByName("reobfJar") ?: findByName("shadowJar") ?: findByName("jar"))
            into(dest)
            doLast {
                println("Copied to plugin directory $dest")
            }
            rename(".*\\.jar", jarName)
        }

        named<DefaultTask>("build") {
            dependsOn("copyJar")
        }
    }
}

tasks.assemble {
    if (copyJar.excludePlatformDependencies.getOrElse(true)) {
        configurations {
            findByName("runtimeClasspath")?.apply {
                val libs = rootProject.extensions.getByType<VersionCatalogsExtension>().named("libs")
                val deps = libs.findBundle("platform").getOrNull()?.getOrNull() ?: emptyList()
                val oraxenLibsDeps = libs.findBundle("oraxenlibs-core").getOrNull()?.getOrNull() ?: emptyList()

                val unwanted = (deps + oraxenLibsDeps).map { it.group to it.name }
                unwanted.forEach {
                    exclude(group = it.first, module = it.second)
                }

                println("Excluded ${unwanted.size} platform dependencies from runtimeClasspath")
            }
            runtimeClasspath {
                exclude(group = "org.jetbrains.kotlin")
                exclude(group = "org.jetbrains.kotlinx")
            }
        }
    }
}
