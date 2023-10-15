import libraries.bookShop.BookShop

plugins {
    java
    applyPlugin(Global.Plugin.SpringBoot)
    applyPlugin(Global.Plugin.SpringDependencyManagement)
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
    implementation(Global.Modules.Utils.JDBC)
    implementation(Global.Modules.Utils.Time)
    implementation(Global.Modules.Utils.Utils)

    implementation(BookShop.libSpringBootStarter)
    annotationProcessor(BookShop.libSpringBootConfigurationProcessor)
    implementation(BookShop.libSpringBootWebStarter)
    implementation(BookShop.libSpringBootThymeleafStarter)
    implementation(BookShop.libSpringBootJdbcStarter)
    implementation(BookShop.libSpringBootJpaStarter)

    compileOnly(BookShop.libLombok)
    annotationProcessor(BookShop.libLombok)

    implementation(BookShop.libPostgresql)

    developmentOnly(BookShop.libSpringBootDevTools)

    testImplementation(kotlin("test"))
    testImplementation(BookShop.testLibSpringBootTestStarter)
}

tasks.withType<Test> {
    useJUnitPlatform()
}
