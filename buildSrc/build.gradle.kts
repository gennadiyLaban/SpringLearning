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
