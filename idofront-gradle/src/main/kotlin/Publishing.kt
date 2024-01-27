import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension

fun PublishingExtension.addOraxenRepo(
    project: Project,
) {
    repositories {
        maven {
            val repo = "https://repo.oraxen.com/"
            val isSnapshot = System.getenv("IS_SNAPSHOT") == "true"
            val url = if (isSnapshot) repo + "snapshots" else repo + "releases"
            setUrl(url)
            credentials {
                username = project.findProperty("oraxenMavenUsername") as String?
                password = project.findProperty("oraxenMavenPassword") as String?
            }
        }
    }
}
