import libraries.timeserver.TimeServer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    applyPlugin(TimeServer.pluginKotlin)
    applyPlugin(TimeServer.pluginSpringBoot)
    applyPlugin(TimeServer.pluginSpringDependencyManagement)
}

group = "org.laban"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = Global.Version.JAVA_VERSION
}

dependencies {
    implementation(TimeServer.libSpringBootStarter)
    annotationProcessor(TimeServer.libSpringBootConfigurationProcessor)

    compileOnly(TimeServer.libLombok)
    annotationProcessor(TimeServer.libLombok)

    testImplementation(kotlin("test"))
    testImplementation(TimeServer.testLibSpringBootTestStarter)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = Global.Version.JAVA
}
