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
    object Log4jApi : Library("org.apache.logging.log4j", "log4j-api", Versions.LOG_4_J)
    object Log4jCore : Library("org.apache.logging.log4j", "log4j-core", Versions.LOG_4_J)

    object SpringContext : Library("org.springframework", "spring-context", Versions.SPRING_CONTEXT)
    object SpringWebMVC : Library("org.springframework", "spring-webmvc", Versions.SPRING_CONTEXT)

    object ServletApi : Library("javax.servlet", "javax.servlet-api", Versions.SERVLET_API)
    object ThymeleafSpring5 : Library("org.thymeleaf", "thymeleaf-spring5", Versions.THYMELEAF_SPRING5)
    object Lombok : Library("org.projectlombok", "lombok", Versions.LOMBOK)

    object JavaxAnnotationApi : Library("javax.annotation", "javax.annotation-api", Versions.JAVAX_ANNOTATION_API)
    object JavaxValidationsApi : Library("javax.validation", "validation-api", Versions.JAVAX_VALIDATION_API)
    object HibernateValidator : Library("org.hibernate.validator", "hibernate-validator", Versions.HIBERNATE_VALIDATOR)
    object HibernateValidatorAnnotationProcessor : Library("org.hibernate.validator", "hibernate-validator-annotation-processor", Versions.HIBERNATE_VALIDATOR)

    object SpringSecurityCore : Library("org.springframework.security", "spring-security-core", Versions.SPRING_SECURITY)
    object SpringSecurityWeb  : Library("org.springframework.security", "spring-security-web", Versions.SPRING_SECURITY)
    object SpringSecurityConfig : Library("org.springframework.security", "spring-security-config", Versions.SPRING_SECURITY)

    object SpringJDBC : Library("org.springframework", "spring-jdbc", Versions.SPRING_CONTEXT)
    object H2DB : Library("com.h2database", "h2", Versions.H2_DB)
    object CommonsFileUpload : Library("commons-fileupload", "commons-fileupload", Versions.COMMONS_FILE_UPLOAD)
    object CommonsIO : Library("commons-io", "commons-io", Versions.COMMONS_IO)
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
