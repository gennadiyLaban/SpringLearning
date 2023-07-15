import libraries.utils.jdbc.UtilsJdbc

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
    implementation(UtilsJdbc.SpringJDBC)
    implementation(UtilsJdbc.SpringCore)
    implementation(UtilsJdbc.Slf4jApi)

    testImplementation(kotlin("test"))
}

tasks.withType<Test> {
    useJUnitPlatform()
}
