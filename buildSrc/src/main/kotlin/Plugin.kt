import org.gradle.plugin.use.PluginDependenciesSpec
import java.lang.IllegalStateException

open class Plugin (
    val id: String,
    val version: String,
)

fun PluginDependenciesSpec.applyPlugin(plugin: Plugin) {
    id(plugin.id).version(plugin.version)
}
