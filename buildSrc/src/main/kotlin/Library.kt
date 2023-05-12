import org.gradle.api.Action
import org.gradle.api.artifacts.ExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
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
    object SpringContext : Library("org.springframework", "spring-context", Versions.SPRING_CONTEXT)
    object SpringWebMVC : Library("org.springframework", "spring-webmvc", Versions.SPRING_CONTEXT)
    object ServletApi : Library("javax.servlet", "servlet-api", Versions.SERVLET_API)

    object Log4j : Library("log4j", "log4j", Versions.LOG_4_J)
    object ThymeleafSpring5 : Library("org.thymeleaf", "thymeleaf-spring5", Versions.THYMELEAF_SPRING5)
    object Lombok : Library("org.projectlombok", "lombok", Versions.LOMBOK)
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
