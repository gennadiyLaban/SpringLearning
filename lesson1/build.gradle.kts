plugins {
    java
    applyPlugin(Global.Plugin.KotlinJVM)
}

group = "org.laban"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = Global.Version.JAVA_VERSION
}

dependencies {
    implementation(Global.Library.SpringCore)
    implementation(Global.Library.SpringContext)
    implementation(Global.Library.JakartaAnnotationApi)
    implementation(Global.Library.SnakeYaml)
    implementation(Global.Library.ApacheCommonLang3)

    compileOnly(Global.Library.Lombok)
    annotationProcessor(Global.Library.Lombok)

    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
