import org.gradle.plugin.use.PluginDependenciesSpec
import java.lang.IllegalStateException

open class Plugin (
    val id: String,
    val version: String,
) {
    object KotlinJVM: Plugin(
        id = "org.jetbrains.kotlin.jvm",
        version = Versions.KOTLIN,
    )

    class Builder {
        var id: String? = null
        var version: String? = null

        fun id(value: String) = apply { this.id = value }
        fun version(value: String) = apply { this.version = value }

        fun build(): Plugin {
            return Plugin(
                id = id ?: throw IllegalStateException("id must be initialized"),
                version = version ?: throw IllegalStateException("version must be initialized")
            )
        }
    }

    companion object {
        fun build(buildAction: Builder.() -> Unit): Plugin {
            return Builder().apply(buildAction).build()
        }
    }
}

fun PluginDependenciesSpec.applyPlugin(plugin: Plugin) {
    id(plugin.id).version(plugin.version)
}
