import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    applyPlugin(Global.Plugin.KotlinJVM)
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"



dependencies {
    // https://mvnrepository.com/artifact/org.springframework/spring-context
    implementation(Global.Library.SpringContext)
    implementation(Global.Library.Log4jApi)
    implementation(Global.Library.Log4jCore)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = Global.Version.JAVA
}

application {
    mainClass.set("MainKt")
}
