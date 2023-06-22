package libraries.timeserver

import Library
import Plugin

object TimeServer {
    val pluginKotlin = Global.Plugin.KotlinJVM
    val pluginSpringBoot = Global.Plugin.SpringBoot
    val pluginSpringDependencyManagement = Global.Plugin.SpringDependencyManagement

    val libLombok = Global.Library.Lombok
    val libSpringBootStarter = Global.Library.SpringBootStarter
    val libSpringBootConfigurationProcessor = Global.Library.SpringBootConfigurationProcessor

    val testLibSpringBootTestStarter = Global.Library.SpringBootTestStarter
}
