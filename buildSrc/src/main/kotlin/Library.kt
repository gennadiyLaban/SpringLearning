import org.gradle.api.Action
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.accessors.runtime.addExternalModuleDependencyTo

sealed class Library (
    val group: String,
    val name: String,
    val version: String? = null,
    val configuration: String? = null,
    val classifier: String? = null,
    val ext: String? = null,
    val dependencyConfiguration: Action<ExternalModuleDependency>? = null,
) {
    object StringContext : Library(
        group = "org.springframework",
        name = "spring-context",
        version = Versions.SPRING_CONTEXT
    )
}

fun DependencyHandler.implementation(library: Library): ExternalModuleDependency {
    return addExternalModuleDependencyTo(
        dependencyHandler = this,
        targetConfiguration = "implementation",
        group = library.group,
        name = library.name,
        version = library.version,
        configuration = library.configuration,
        classifier = library.classifier,
        ext = library.ext,
        action = library.dependencyConfiguration
    )
}