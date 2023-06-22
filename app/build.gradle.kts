import libraries.cleanspring.CleanSpring
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    applyPlugin(Global.Plugin.KotlinJVM)
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"



dependencies {
    // https://mvnrepository.com/artifact/org.springframework/spring-context
    implementation(CleanSpring.SpringContext)
    implementation(CleanSpring.Log4jApi)
    implementation(CleanSpring.Log4jCore)

    implementation(project(":lesson-2"))

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = Global.Version.JAVA
}

application {
    mainClass.set("MainKt")
}
