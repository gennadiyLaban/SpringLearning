import libraries.bookShop.BookShop

plugins {
    java
    applyPlugin(libraries.bookShop.BookShop.pluginSpringBoot)
    applyPlugin(libraries.bookShop.BookShop.pluginSpringDependencyManagement)
}

group = "org.laban.learning.spring"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = Versions.JAVA_VERSION
}

dependencies {
    implementation(BookShop.libSpringBootStarter)
    annotationProcessor(BookShop.libSpringBootConfigurationProcessor)
    implementation(BookShop.libSpringBootWebStarter)
    implementation(BookShop.libSpringBootThymeleafStarter)

    compileOnly(BookShop.libLombok)
    annotationProcessor(BookShop.libLombok)

    testImplementation(kotlin("test"))
    testImplementation(libraries.timeserver.TimeServer.testLibSpringBootTestStarter)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
