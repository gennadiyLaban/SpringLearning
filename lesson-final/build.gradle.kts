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
    implementation(Global.Library.SpringBootSecurityStarter)
    implementation(Global.Library.SpringDocOpenApiWebMvcUiStarter)
    runtimeOnly(Global.Library.Postgresql)
    implementation(Global.Library.SpringKafka)

    annotationProcessor(Global.Library.SpringBootConfigurationProcessor)

    compileOnly(Global.Library.Lombok)
    annotationProcessor(Global.Library.Lombok)

    implementation(Global.Library.MapStructCore)
    annotationProcessor(Global.Library.MapStructProcessor)

    implementation(Global.Library.ApacheCommonLang3)

    testImplementation(kotlin("test"))
    testImplementation(Global.Library.SpringBootTestStarter)
    testCompileOnly(Global.Library.Lombok)
    testAnnotationProcessor(Global.Library.Lombok)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
