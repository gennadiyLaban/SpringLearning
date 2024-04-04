import net.ltgt.gradle.errorprone.errorprone

plugins {
    `java-library`
    applyPlugin(Global.Plugin.KotlinJVM)
    applyPlugin(Global.Plugin.ErrorProne)
}

group = "org.laban"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = Global.Version.JAVA_VERSION
}

dependencies {
    compileOnly(Global.Library.Lombok)
    annotationProcessor(Global.Library.Lombok)

    implementation(Global.Library.ApacheCommonLang3)

    errorProne(Global.Library.ErrorProne)

    testImplementation(kotlin("test"))
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
