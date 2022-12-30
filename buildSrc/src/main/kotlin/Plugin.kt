import org.gradle.plugin.use.PluginDependenciesSpec

sealed class Plugin (
    val id: String,
    val version: String,
) {
    object KotlinJVM: Plugin(
        id = "org.jetbrains.kotlin.jvm",
        version = Versions.KOTLIN,
    )
}

fun PluginDependenciesSpec.applyPlugin(plugin: Plugin) {
    id(plugin.id).version(plugin.version)
}
