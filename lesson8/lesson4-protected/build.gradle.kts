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
    implementation(Global.Library.SpringBootWebStarter)
    implementation(Global.Library.SpringBootDataJpaStarter)
    implementation(Global.Library.SpringBootValidationStarter)
    implementation(Global.Library.SpringDocOpenApiWebMvcUiStarter)
    runtimeOnly(Global.Library.Postgresql)

    annotationProcessor(Global.Library.SpringBootConfigurationProcessor)

    compileOnly(Global.Library.Lombok)
    annotationProcessor(Global.Library.Lombok)

    implementation(Global.Library.MapStructCore)
    annotationProcessor(Global.Library.MapStructProcessor)

    implementation(Global.Library.ApacheCommonLang3)

    developmentOnly(Global.Library.SpringBootDevTools)

    testImplementation(kotlin("test"))
    testImplementation(Global.Library.SpringBootTestStarter)
    testImplementation(Global.Library.JavacrumbsJsonUnit)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
