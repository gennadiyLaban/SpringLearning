addMavenCentralForAllIn(project)

fun addMavenCentralForAllIn(project: Project) {
    println("add maven central for: ${project.name}")
    project.repositories {
        mavenCentral()
    }
    project.childProjects.forEach { (_, subproject) ->
        addMavenCentralForAllIn(subproject)
    }
}
