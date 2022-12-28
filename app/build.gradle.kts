import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Libraries.Versions.KOTLIN_VERSION
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

dependencies {
    // https://mvnrepository.com/artifact/org.springframework/spring-context
    implementation("org.springframework:spring-context:6.0.3")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = Libraries.Versions.JAVA_VERSION
}

application {
    mainClass.set("MainKt")
}