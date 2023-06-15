import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import properties.BaseEnvironmentProvider
import properties.BaseProjectProperties

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
    implementation(Library.Log4jApi)
    implementation(Library.Log4jCore)
    implementation(Library.ThymeleafSpring5)
    implementation(Library.JavaxAnnotationApi)
    implementation(Library.JavaxValidationsApi)
    implementation(Library.HibernateValidator)

    implementation(Library.SpringSecurityCore)
    implementation(Library.SpringSecurityWeb)
    implementation(Library.SpringSecurityConfig)

    implementation(Library.SpringJDBC)
    implementation(Library.H2DB)
    implementation(Library.CommonsFileUpload)
    implementation(Library.CommonsIO)

    annotationProcessor(Library.HibernateValidatorAnnotationProcessor)
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

val projectProperties = BaseProjectProperties.readFrom(project)
val environmentProvider = BaseEnvironmentProvider(projectProperties)
environmentProvider.writeEnvValues(project)