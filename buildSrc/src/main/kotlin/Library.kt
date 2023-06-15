import org.gradle.api.Action
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.kotlin.dsl.accessors.runtime.addExternalModuleDependencyTo
import java.lang.IllegalStateException

open class Library (
    val group: String,
    val name: String,
    val version: String? = null,
    val configuration: String? = null,
    val classifier: String? = null,
    val ext: String? = null,
    val dependencyConfiguration: Action<ExternalModuleDependency>? = null,
) {
    class Builder {
        var group: String? = null
        var name: String? = null
        var version: String? = null
        var configuration: String? = null
        var classifier: String? = null
        var ext: String? = null
        var dependencyConfiguration: Action<ExternalModuleDependency>? = null

        fun group(value: String) = apply { this.group = value }
        fun name(value: String) = apply { this.name = value }
        fun version(value: String?) = apply { this.version = value }

        fun build(): Library {
            val group = this.group ?: throw IllegalStateException("group must be initialized")
            val name = this.name ?: throw  IllegalStateException("name must be initialized")

            return Library(
                group = group,
                name = name,
                version = version,
                configuration = configuration,
                classifier = classifier,
                ext = ext,
                dependencyConfiguration = dependencyConfiguration
            )
        }
    }

    companion object {
        fun build(buildAction: Builder.() -> Unit ): Library {
            return Builder().apply(buildAction).build()
        }

        fun builder() = Builder()
    }
}

fun DependencyHandler.implementation(library: Library): ExternalModuleDependency {
    return addDepsLibrary(library, targetConfiguration = "implementation")
}

fun DependencyHandler.annotationProcessor(library: Library): ExternalModuleDependency {
    return addDepsLibrary(library, targetConfiguration = "annotationProcessor")
}

fun DependencyHandler.providedCompile(library: Library): ExternalModuleDependency {
    return addDepsLibrary(library, targetConfiguration = "providedCompile")
}

fun DependencyHandler.providedAnnotationProcessor(library: Library) {
    providedCompile(library)
    annotationProcessor(library)
}

internal fun DependencyHandler.addDepsLibrary(
    library: Library,
    targetConfiguration: String
): ExternalModuleDependency {
    return addExternalModuleDependencyTo(
        dependencyHandler = this,
        targetConfiguration = targetConfiguration,
        group = library.group,
        name = library.name,
        version = library.version,
        configuration = library.configuration,
        classifier = library.classifier,
        ext = library.ext,
        action = library.dependencyConfiguration
    )
}
