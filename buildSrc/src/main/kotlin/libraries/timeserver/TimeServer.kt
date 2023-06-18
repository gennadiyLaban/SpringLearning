package libraries.timeserver

import Library
import Plugin

object TimeServer {
    val pluginKotlin = Plugin.KotlinJVM
    val pluginSpringBoot = pluginOf {
        id = "org.springframework.boot"
        version = Version.PLUGIN_SPRING_BOOT
    }
    val pluginSpringDependencyManagement = pluginOf {
        id = "io.spring.dependency-management"
        version = Version.PLUGIN_SPRING_DEP_MANAGEMENT
    }


    val libLombok = libOf {
        group = "org.projectlombok"
        name = "lombok"
        version = Version.LIB_LOMBOK
    }
    val libSpringBootStarter = libOf {
        group = "org.springframework.boot"
        name = "spring-boot-starter"
    }
    val libSpringBootConfigurationProcessor = libOf {
        group = "org.springframework.boot"
        name = "spring-boot-configuration-processor"
        version = Version.PLUGIN_SPRING_BOOT
    }



    val testLibSpringBootTestStarter = libOf {
        group = "org.springframework.boot"
        name = "spring-boot-starter-test"
    }

    object Version {
        const val LIB_LOMBOK = "1.18.26"

        const val PLUGIN_SPRING_BOOT = "2.7.12"
        const val PLUGIN_SPRING_DEP_MANAGEMENT = "1.0.15.RELEASE"
    }

    private fun libOf(buildAction: Library.Builder.() -> Unit): Library {
        return Library.build(buildAction)
    }
    private fun pluginOf(buildAction: Plugin.Builder.() -> Unit): Plugin {
        return Plugin.build(buildAction)
    }
}
