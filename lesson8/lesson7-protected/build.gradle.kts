import net.ltgt.gradle.errorprone.errorprone

plugins {
    java
    applyPlugin(Global.Plugin.KotlinJVM)
    applyPlugin(Global.Plugin.SpringBoot)
    applyPlugin(Global.Plugin.SpringDependencyManagement)
    applyPlugin(Global.Plugin.ErrorProne)
}

group = "org.laban"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = Global.Version.JAVA_VERSION
}

dependencies {
    implementation(Global.Library.SpringBootStarter)
    implementation(Global.Library.SpringBootValidationStarter)
    implementation(Global.Library.SpringBootWebFluxStarter)
    implementation(Global.Library.SpringBootSecurityStarter)
    implementation(Global.Library.SpringBootMongoDbReactiveStarter)

    annotationProcessor(Global.Library.SpringBootConfigurationProcessor)

    compileOnly(Global.Library.Lombok)
    annotationProcessor(Global.Library.Lombok)

    implementation(Global.Library.MapStructCore)
    annotationProcessor(Global.Library.MapStructProcessor)

    implementation(Global.Library.ApacheCommonLang3)

    errorProne(Global.Library.ErrorProne)

    testImplementation(kotlin("test"))
    testImplementation(Global.Library.SpringBootTestStarter)
    testImplementation(Global.Library.JavacrumbsJsonUnit)
}


tasks.withType<JavaCompile> {
    options.errorprone {
        disableWarningsInGeneratedCode.set(true)
    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}
