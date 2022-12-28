project.childProjects.forEach { (_, subproject) ->
    subproject.repositories {
        mavenCentral()
    }
}