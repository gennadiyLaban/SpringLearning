import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    applyPlugin(Plugin.KotlinJVM)
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"



dependencies {
    // https://mvnrepository.com/artifact/org.springframework/spring-context
    implementation(Library.SpringContext)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = Versions.JAVA
}

application {
    mainClass.set("MainKt")
}