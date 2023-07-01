import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.initialization.Settings
import org.gradle.kotlin.dsl.project

open class ProjectModule(
    val name: String,
    val configuration: String? = null
)

fun DependencyHandler.implementation(projectModule: ProjectModule) {
    add("implementation", project(":${projectModule.name}", projectModule.configuration))
}

fun Settings.include(projectModule: ProjectModule) {
    include(projectModule.name)
}
