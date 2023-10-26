plugins {
    java
    applyPlugin(Global.Plugin.KotlinJVM)
    applyPlugin(Global.Plugin.SpringBoot)
    applyPlugin(Global.Plugin.SpringDependencyManagement)
}

group = "org.laban"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = Global.Version.JAVA_VERSION
}

dependencies {
    implementation(Global.Library.SpringBootStarter)
    implementation(Global.Library.SpringShellStarter)
    annotationProcessor(Global.Library.SpringBootConfigurationProcessor)

    compileOnly(Global.Library.Lombok)
    annotationProcessor(Global.Library.Lombok)

    // developmentOnly(Global.Library.SpringBootDevTools)

    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
