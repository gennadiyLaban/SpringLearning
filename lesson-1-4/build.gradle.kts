import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    applyPlugin(Plugin.KotlinJVM)
    war
}

group = "org.example"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = Versions.JAVA
}

dependencies {
    implementation(Library.SpringContext)
    implementation(Library.SpringWebMVC)
    implementation(Library.ServletApi)
    implementation(Library.Log4j)
    implementation(Library.ThymeleafSpring5)
    providedAnnotationProcessor(Library.Lombok)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
tasks.withType<War> {
    webAppDirectory.set(file("src/main/webapp"))
    archiveFileName.set("spring_learning.war")
}