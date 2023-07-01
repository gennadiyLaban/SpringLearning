import libraries.bookShop.BookShop

plugins {
    java
    applyPlugin(libraries.bookShop.BookShop.pluginSpringBoot)
    applyPlugin(libraries.bookShop.BookShop.pluginSpringDependencyManagement)
}

configurations {
    developmentOnly
    runtimeClasspath {
        extendsFrom(developmentOnly.get())
    }
}

group = "org.laban.learning.spring"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = Global.Version.JAVA_VERSION
}

dependencies {
    implementation(project(":utils:jdbc"))

    implementation(BookShop.libSpringBootStarter)
    annotationProcessor(BookShop.libSpringBootConfigurationProcessor)
    implementation(BookShop.libSpringBootWebStarter)
    implementation(BookShop.libSpringBootThymeleafStarter)
    implementation(BookShop.libSpringBootJdbcStarter)

    compileOnly(BookShop.libLombok)
    annotationProcessor(BookShop.libLombok)

    implementation(BookShop.libH2Database)

    developmentOnly(BookShop.libSpringBootDevTools)

    testImplementation(kotlin("test"))
    testImplementation(libraries.timeserver.TimeServer.testLibSpringBootTestStarter)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
