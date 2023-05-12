import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
    id("groovy")
}

repositories {
    mavenCentral()
}

dependencies {
    gradleApi()
    gradleKotlinDsl()
    localGroovy()
}