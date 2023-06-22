import libraries.cleanspring.CleanSpring
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import properties.BaseEnvironmentProvider
import properties.BaseProjectProperties

plugins {
    applyPlugin(libraries.cleanspring.CleanSpring.KotlinJVM)
    war
}

group = "org.example"
version = "1.0-SNAPSHOT"

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = Global.Version.JAVA
}

dependencies {
    implementation(project(":utils:jdbc"))

    implementation(CleanSpring.SpringContext)
    implementation(CleanSpring.SpringWebMVC)
    implementation(CleanSpring.ServletApi)
    implementation(CleanSpring.Log4jApi)
    implementation(CleanSpring.Log4jCore)
    implementation(CleanSpring.ThymeleafSpring5)
    implementation(CleanSpring.JavaxAnnotationApi)
    implementation(CleanSpring.JavaxValidationsApi)
    implementation(CleanSpring.HibernateValidator)

    implementation(CleanSpring.SpringSecurityCore)
    implementation(CleanSpring.SpringSecurityWeb)
    implementation(CleanSpring.SpringSecurityConfig)

    implementation(CleanSpring.SpringJDBC)
    implementation(CleanSpring.H2DB)
    implementation(CleanSpring.CommonsFileUpload)
    implementation(CleanSpring.CommonsIO)

    annotationProcessor(CleanSpring.HibernateValidatorAnnotationProcessor)
    providedAnnotationProcessor(CleanSpring.Lombok)

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
